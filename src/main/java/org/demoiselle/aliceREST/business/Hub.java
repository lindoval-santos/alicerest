package org.demoiselle.aliceREST.business;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hub implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @SerializedName("mode")
    @Expose
	private String mode = "";
    
    @SerializedName("verify_token")
    @Expose
	private String verify_token = "";
    
    @SerializedName("challenge")
    @Expose
	private String challenge = "";
	
	public Hub(){}
	
	public Hub(String mode, String verify_token, String challenge){
		this.mode = mode;
		this.verify_token = verify_token;
		this.challenge = challenge;
	}

	
	public String getMode() {
		return this.mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getVerify_token() {
		return verify_token;
	}
	public void setVerify_token(String verify_token) {
		this.verify_token = verify_token;
	}
	public String getChallenge() {
		return challenge;
	}
	public void setChallenge(String challenge) {
		this.challenge = challenge;
	}
}
