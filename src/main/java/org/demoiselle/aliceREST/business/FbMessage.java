package org.demoiselle.aliceREST.business;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FbMessage {
	
	@SerializedName("mid")
	@Expose
	private String mid;
	
	@SerializedName("seq")
	@Expose
	private Long seq;
	
	@SerializedName("text")
	@Expose
	private String text;
	
	@SerializedName("attachment")
	@Expose	
	private Atachment attachment;
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public Atachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Atachment attachment) {
		this.attachment = attachment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mid == null) ? 0 : mid.hashCode());
		result = prime * result + ((seq == null) ? 0 : seq.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		FbMessage other = (FbMessage) obj;
		if (mid == null) {
			if (other.mid != null)
				return false;
		} else if (!mid.equals(other.mid))
			return false;
		if (seq == null) {
			if (other.seq != null)
				return false;
		} else if (!seq.equals(other.seq))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
	
}
