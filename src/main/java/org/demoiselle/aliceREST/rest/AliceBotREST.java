package org.demoiselle.aliceREST.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.demoiselle.aliceREST.business.AliceBotBC;

@Api(value = "query")
@Path("query")
public class AliceBotREST {

	@Inject
	private AliceBotBC bc;

	@GET
	@Path("ask/{mensagem}")
	@Produces("application/json")
	@ApiOperation(value = "Responde uma questão conforme a base de conhecimento", notes = "Robot em REST", response = Resposta.class, authorizations = {
	@Authorization(value = "basicAuth") })
	//@ApiResponses(value = { @ApiResponse(code = 400, message = "Parâmetro(s) em desacordo com a especificação.") })
	public Resposta consultar(@PathParam("mensagem") String mensagem) throws Exception {
		String s = bc.questionar(mensagem);
		Resposta r = new Resposta(s);
		return r;
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
