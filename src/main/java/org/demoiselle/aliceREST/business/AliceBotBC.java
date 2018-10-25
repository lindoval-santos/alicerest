package org.demoiselle.aliceREST.business;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.demoiselle.aliceREST.chatter.bitoflife.chatterbean.AliceBot;
import org.demoiselle.aliceREST.chatter.bitoflife.chatterbean.AliceBotMother;
import org.demoiselle.aliceREST.chatter.bitoflife.chatterbean.text.Response;
import org.demoiselle.aliceREST.persistence.ConfigDAO;
import org.demoiselle.aliceREST.rest.Resposta;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;

@BusinessController
public class AliceBotBC 
{

    private static final AliceBotMother mother = new AliceBotMother();
    
    private ConfigDAO dao = new ConfigDAO();
  
    private static AliceBot bot;
    
    final String PAGE_ACCESS_TOKEN = "EAAPWgwdUPr4BAMZBl3PY32XRt6ZAgidRExe10LLXNUDs6ufr17Td6EKvvx5CnAVAQxPnRg7n13LjjO8nvpMfkCeD27ZBgZC6c9OgLkCIZB7IDS79COFHo5ayY9YeiW9W6uYUbaud5DAidoW9XVOmQtjEpIAb90LEoJAWE4GVYI8gkiaK39YTg";
    
    public String getFbAccessToken(){
    	return PAGE_ACCESS_TOKEN;
    }
	
	private static void setUp(String cliente) throws Exception{
	  bot = Bots.getBot(cliente);
      if (bot == null){
	    mother.setUp("");
	    bot = mother.newInstance();
      }
	}
	
	@SuppressWarnings("static-access")
	public Resposta questionar(String cliente, String q)throws Exception{
		bot = null;
		System.gc();
		this.setUp(cliente);
		Response response = bot.respond(q);
		String r = response.getOriginal().trim();
		String that = response.getSentences(0).getNormalized().trim();
		that = that.endsWith(".") && that.length() >= 1?that.substring(0,that.length()-1):that;
		that = Normalizer.normalize(that, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		r = r.replace("&lt;br&gt;", "<br>").replace("&lt;b&gt;", "<b>").replace("&lt;&#47;b&gt;", "</b>");
		Resposta resp = new Resposta(q, r);
		Bots.addBot(cliente, bot);
		return resp;
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
	
	static class Bots{
		
		public Bots(){Locale.setDefault(new Locale("pt", "BR"));};
		
		private static Map<String, AliceBot> botsGuardados = new HashMap<String, AliceBot>();
		
		public static void addBot(String dono, AliceBot bot){
			if (dono == null || "".equals(dono))
				return;
			System.out.println("Empilhando bot para: " + dono);
			bot.setUltimaVezUsado(Calendar.getInstance());
			if (!botsGuardados.containsKey(dono))
				botsGuardados.put(dono, bot);
		}
		
		public static AliceBot getBot(String dono){
			checkUso();
			if(dono == null || "".equals(dono))
				return null;
			System.out.println("Empilhando bot para: " + dono);
			if(botsGuardados.containsKey(dono))
				return botsGuardados.get(dono);
			
			return null;
		}
		
		public static void checkUso(){
			List<String> listRevogar = new ArrayList<String>();
			boolean algumRevogar = false;
			
			for(String dono: botsGuardados.keySet()){
				AliceBot b = botsGuardados.get(dono);
				long difMil = (Calendar.getInstance().getTimeInMillis() - b.getUltimaVezUsado().getTimeInMillis());
				long difMin = difMil / (60 * 1000);
				if (difMin >= 5){
					algumRevogar = true;
					listRevogar.add(dono);
				}
			}
			
			if(algumRevogar){
				for (String s: listRevogar){
					botsGuardados.remove(s);
				}
			}
			System.gc();
		}
	}
	
}