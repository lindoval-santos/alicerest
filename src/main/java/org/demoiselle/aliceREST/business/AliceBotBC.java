package org.demoiselle.aliceREST.business;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;

import org.demoiselle.aliceREST.chatter.bitoflife.chatterbean.AliceBot;
import org.demoiselle.aliceREST.chatter.bitoflife.chatterbean.AliceBotMother;
import org.demoiselle.aliceREST.chatter.bitoflife.chatterbean.text.Response;
import org.demoiselle.aliceREST.persistence.ConfigDAO;
import org.demoiselle.aliceREST.rest.Resposta;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;

@BusinessController
public class AliceBotBC 
{

    private final AliceBotMother mother = new AliceBotMother();
    
    private ConfigDAO dao = new ConfigDAO();
  
    private AliceBot bot;
	
	public void setUp() throws Exception{
      if (bot == null){
    	Locale.setDefault(new Locale("pt", "BR"));
	    mother.setUp("");
	    bot = mother.newInstance();
      }
	}
	
	public Resposta questionar(QuestaoBody q)throws Exception{
		this.setUp();
		Response response = bot.respond(q.getQuestao(), q.getTopic().toUpperCase(), q.getThat().toUpperCase());
		String s = response.getOriginal().trim();
		String that = response.getSentences(1).getOriginal().trim();
		String topic = response.getSentences(0).getOriginal();
		topic = topic.toUpperCase();
		that = that.endsWith(".") && that.length() >= 1?that.substring(0,that.length()-1):that;
		that = Normalizer.normalize(that, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		that = that.toUpperCase();
		s = s.replace("&lt;br&gt;", "<br>").replace("&lt;b&gt;", "<b>").replace("&lt;&#47;b&gt;", "</b>");
		Resposta r = new Resposta(topic, that, q.getQuestao(),s);
		return r;
	}

	//recarregar paths a partir do banco
	public void reLoadConfigs(){
		mother.loadConfig();
	}

	public List<ConfigBody> getConfigs(){
		//Configuracao c = new Configuracao();
		//return c.getPropriedades();
		return dao.selectAll();
	}

	public ConfigBody getConfigByNome(String nome){
		return dao.load(new ConfigBody(nome, ""));
	}

	public String insertConfig(ConfigBody c){
		String s = "";
		s = dao.insert(c);
		if ("Erro".equals(s))
			s = "Configuração não alterada devido a um erro interno.";
		return s;
	}
	
}