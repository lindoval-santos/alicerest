package org.demoiselle.aliceREST.rest;

import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.jaxrs.config.BeanConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@SwaggerDefinition(
		info = @Info(description = Api.DESCRIPTION, title = "Serviços de Chat", version = "1.0.0-SNAPSHOT") , 
		consumes = {"application/json" }, 
		produces = { "application/json" }, 
		schemes = { SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS })
@ApplicationPath("bot")
public class Api extends Application {

	public static final String DESCRIPTION = "As `APIs de Chat` são serviços que estão expostos através de `HTTP REST` com suporte a Cross-Domain oferecendo operações que permitem as aplicações realizarem diálogos com um Bot.";

	public Api() {
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setVersion("1.0.0-SNAPSHOT");
		beanConfig.setBasePath("/alicerest/bot");
		beanConfig.setResourcePackage("org.demoiselle.aliceREST");
		beanConfig.setScan(true);
	}

}
