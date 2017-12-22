package org.demoiselle.aliceREST.rest;

public class Resposta {

	private String conteudo;
	
	public Resposta(String resposta) {
		this.conteudo = resposta;
	}

	public String getConteudo(){
		return this.conteudo;
	}
	
	public void setConteudo(String _conteudo){
		this.conteudo = _conteudo;
	}
}
