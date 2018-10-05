package org.demoiselle.aliceREST.business;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FBMessageResponse{
	
	public FBMessageResponse(){}
	

	@SerializedName("messaging_type")
	@Expose
	private String messaging_type;

	@SerializedName("recipient")
	@Expose
	private Recipient recipient;
	
	@SerializedName("notification_type")
	@Expose
	private String notification_type = "REGULAR";

	//typing_on: exibir o balão de digitação
    //typing_off: remover o balão de digitação
    //mark_seen: exibir o ícone de confirmação
	@SerializedName("sender_action")
	@Expose
	private String sender_action = "mark_seen"; 
	
	@SerializedName("message")
	@Expose
	private FbMessage message;

	public String getSender_action() {
		return sender_action;
	}

	public void setSender_action(String sender_action) {
		this.sender_action = sender_action;
	}

	public String getMessaging_type() {
		return messaging_type;
	}

	public void setMessaging_type(String messaging_type) {
		this.messaging_type = messaging_type;
	}
	
	public String getNotification_type() {
		return notification_type;
	}

	public void setNotification_type(String notification_type) {
		this.notification_type = notification_type;
	}

	public Recipient getRecipient() {
		return recipient;
	}

	public void setRecipient(Recipient recipient) {
		this.recipient = recipient;
	}

	public FbMessage getMessage() {
		return message;
	}

	public void setMessage(FbMessage message) {
		this.message = message;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result
				+ ((recipient == null) ? 0 : recipient.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FBMessageResponse other = (FBMessageResponse) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (recipient == null) {
			if (other.recipient != null)
				return false;
		} else if (!recipient.equals(other.recipient))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FBMessageResponse [recipient=" + recipient + ", message="
				+ message + "]";
	}

	
}
