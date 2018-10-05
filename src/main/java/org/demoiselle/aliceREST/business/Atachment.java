package org.demoiselle.aliceREST.business;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Atachment {
	
	    //Tipo de anexo. Pode ser image, audio, video, file ou template. Para ativos, o tamanho máximo do arquivo é de 25 MB.
	    @SerializedName("type")
	    @Expose
	    private String type;

	    @SerializedName("payload")
	    @Expose
	    private Object payload = null;

	    public String getType() {
	        return type;
	    }

	    public void setType(String type) {
	        this.type = type;
	    }


	    public Object getPayload() {
	        return payload;
	    }

	    public void setPayload(Object payload) {
	        this.payload = payload;
	    }



}
