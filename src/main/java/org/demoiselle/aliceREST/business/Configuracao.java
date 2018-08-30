package org.demoiselle.aliceREST.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class Configuracao {
	Configuracao(){
		load();
		}
	
	private Set<String> nomes;
	private Properties properties;
	
	private void load(){

        properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/messages.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public List<ConfigBody> getPropriedades(){

		List<ConfigBody> retorno = new ArrayList<ConfigBody>();
		
		nomes = properties.stringPropertyNames();
		
		for(String n: nomes){
			if("application.version".equals(n))
				continue;
			String v = properties.getProperty(n);
			ConfigBody ret = new ConfigBody(n, v);
			retorno.add(ret);
		}
		
		return retorno;
	}
}
