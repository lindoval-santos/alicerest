package org.demoiselle.aliceREST.rest;


public class Resposta {

	private String conteudo;
	private String questao;
	
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
