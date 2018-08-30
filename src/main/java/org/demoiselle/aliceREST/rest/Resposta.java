package org.demoiselle.aliceREST.rest;


public class Resposta {

	private String conteudo;
	private String questao;
	private String that;
	private String topic;
	
	public String getThat() {
		return that;
	}

	public void setThat(String that) {
		this.that = that;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Resposta(String _topic, String _that, String q, String r) {
		this.that = _that;
		this.topic = _topic;
		this.conteudo = r;
		this.questao = q;
	}
	
	public Resposta(String q, String r) {
		this.conteudo = r;
		this.questao = q;
	}

	public Resposta(String resposta) {
		this.conteudo = resposta;
	}

	
	public String getConteudo(){
		return this.conteudo;
	}
	
	public void setConteudo(String _conteudo){
		this.conteudo = _conteudo;
	}
	
	public void setQuestao(String q){
		this.questao = q;
	}
	public String getQuestao(){
		return this.questao;
	}
	
}
