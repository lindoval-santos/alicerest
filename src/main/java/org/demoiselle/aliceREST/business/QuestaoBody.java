package org.demoiselle.aliceREST.business;


public class QuestaoBody{
	
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

	public QuestaoBody(String questao, String that, String topic) {
		this.that = that;
		this.topic = topic;
		this.questao = questao;
	}
	
	public QuestaoBody(String questao) {
		this.questao = questao;
		this.that = "*";
		this.topic = "*";
	}
	
	public void setQuestao(String q){
		this.questao = q;
	}
	public String getQuestao(){
		return this.questao;
	}
}
