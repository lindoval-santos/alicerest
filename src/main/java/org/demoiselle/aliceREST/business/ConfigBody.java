package org.demoiselle.aliceREST.business;

public class ConfigBody {
	
	public ConfigBody(){}
	
	public ConfigBody(String n, String v){
		this.nome = n;
		this.valor = v;
	}
	
	private String nome = "";
	private String valor = "";
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}

}
