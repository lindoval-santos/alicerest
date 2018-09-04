package org.demoiselle.aliceREST.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.demoiselle.aliceREST.business.AliceBotBC;
import org.demoiselle.aliceREST.business.ConfigBody;
import org.demoiselle.aliceREST.business.QuestaoBody;

@Api(value = "query")
@Path("query")
public class AliceBotREST {

	private AliceBotBC bc = new AliceBotBC();

	@GET
	@Path("ask/{questao}/{that}/{topic}")
	@Produces("application/json")
	@Consumes("application/json")
	@ApiOperation(value = "O chatterbot responde uma questão conforme a base de conhecimento", notes = "Robot em REST", response = Resposta.class)
	public Response consultar(@PathParam("questao") String questao,
							  @PathParam("that") String that,
							  @PathParam("topic") String topic) throws Exception {
		if (questao == null || "".equals(questao))
			throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
					.entity("Nenhuma questão informada.").build());
		System.out.println("consultando com os 3 parâmetros");
		Resposta r = null;
		try{
			r = bc.questionar(new QuestaoBody(questao, that == null?"*":that, topic == null?"*":topic));
		}catch(Exception e)
		{
			e.printStackTrace();
			Resposta resp = new Resposta(topic.trim(), that, "Ocorreu um erro, favor recarregar a página para reiniciar.",questao);
			return Response.ok().entity(resp).build();
		}
		return Response.ok().entity(r).build();
	}

	//O chome no Android não consegue construir URL tipo "{questao}/{that}/{topic}", assim, irá consultar este método
	@GET
	@Path("ask/{questao}/{that}")
	@Produces("application/json")
	@Consumes("application/json")
	@ApiOperation(value = "O chatterbot responde uma questão conforme a base de conhecimento", notes = "Robot em REST", response = Resposta.class)
	public Response consultar(@PathParam("questao") String questao,
			  				  @PathParam("that") String that) throws Exception {
		if (questao == null || "".equals(questao))
			throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
					.entity("Nenhuma questão informada.").build());
		System.out.println("consultando com 2 parâmetros");
		return this.consultar(questao, that, "*");
	}
	
	//O chome no Android não consegue construir URL tipo "{questao}/{that}/{topic}", assim, irá consultar este método
	@GET
	@Path("ask/{questao}")
	@Produces("application/json")
	@Consumes("application/json")
	@ApiOperation(value = "O chatterbot responde uma questão conforme a base de conhecimento", notes = "Robot em REST", response = Resposta.class)
	public Response consultar(@PathParam("questao") String questao) throws Exception {
		if (questao == null || "".equals(questao))
			throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
					.entity("Nenhuma questão informada.").build());
		System.out.println("consultando com 1 parâmetro");
		return this.consultar(questao, "*", "*");
	}
	
	
	@POST
	@Path("config/{nome}/{valor}")
	@ApiResponses(value = {@ApiResponse(code = 400, message = "Os parâmetros 'nome' e 'valor' são obrigatórios") })
	@ApiOperation(value = "Insere ou altera o valor de um path da configuração", notes = "Realiza alteração nos paths das configurações", response = ConfigBody.class)
	public Response alterarConfig(ConfigBody config){

		if ((config == null) || ("".equals(config.getNome())) || ("".equals(config.getValor()))){
			throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
					.entity("Os parâmetros 'nome' e 'valor' são obrigatórios").build());
		}
		String s = "";
		s = bc.insertConfig(config);
		return Response.ok().entity(new ConfigBody(s,config.getValor())).build();
	}
	
	@GET
	@Path("config/recupera")
	@Produces("application/json")
	@ApiOperation(value = "Recuperar os valores da configuração", notes = "Fornece listagem dos paths das configurações", response = ConfigBody.class)
	public Response recuperarConfiguracoes() throws Exception {
		List<ConfigBody> result = bc.getConfigs();
		return Response.ok().entity(result).build();
	}


/*	@GET
	@Path("{id}")
	@Produces("application/json")
	public Bookmark load(@PathParam("id") Long id) throws Exception {
		Bookmark result = bc.load(id);

		if (result == null) {
			throw new NotFoundException();
		}

		return result;
	}

	@POST
//	@LoggedIn
	@Transactional
//	@ValidatePayload
	@Produces("application/json")
	@Consumes("application/json")
	public Response insert(Bookmark body, @Context UriInfo uriInfo, @Context HttpHeaders headers) throws Exception {
		
        System.out.println("ALL headers -- "+ headers.getRequestHeaders().toString());
        //System.out.println("'Accept' header -- "+ headers.getHeaderString("Accept"));
        System.out.println("'TestCookie' value -- "+ headers.getCookies().get("TestCookie").getValue());
		checkId(body);

		String id = bc.insert(body).getId().toString();
		URI location = uriInfo.getRequestUriBuilder().path(id).build();

		return Response.created(location).entity(id).build();
	}

	@PUT
//	@LoggedIn
	@Path("{id}")
	@Transactional
//	@ValidatePayload
	@Produces("application/json")
	@Consumes("application/json")
	public void update(@PathParam("id") Long id, Bookmark body) throws Exception {
		checkId(body);
		load(id);

		body.setId(id);
		bc.update(body);
	}

	@DELETE
//	@LoggedIn
	@Path("{id}")
	@Transactional
	public void delete(@PathParam("id") Long id) throws Exception {
		load(id);
		bc.delete(id);
	}

	@DELETE
//	@LoggedIn
	@Transactional
	public void delete(List<Long> ids) throws Exception {
		bc.delete(ids);
	}

	private void checkId(Bookmark entity) throws Exception {
		if (entity.getId() != null) {
			throw new BadRequestException();
		}
	}*/
}
