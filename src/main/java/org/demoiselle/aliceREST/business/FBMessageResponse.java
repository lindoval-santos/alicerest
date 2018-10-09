package org.demoiselle.aliceREST.business;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FBMessageResponse{
	
	public FBMessageResponse(){}
	

	@SerializedName("recipient")
	@Expose
	private Recipient recipient;
	
	@SerializedName("message")
	@Expose
	private FbMessage message;

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
