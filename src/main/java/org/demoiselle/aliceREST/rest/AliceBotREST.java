package org.demoiselle.aliceREST.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.demoiselle.aliceREST.business.AliceBotBC;
import org.demoiselle.aliceREST.business.ConfigBody;
import org.demoiselle.aliceREST.business.FBMessageResponse;
import org.demoiselle.aliceREST.business.FbMessage;
import org.demoiselle.aliceREST.business.Hub;
import org.demoiselle.aliceREST.business.Recipient;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Api(value = "query")
@Path("query")
public class AliceBotREST {

	private AliceBotBC bc = new AliceBotBC();
	
	final String VERIFY_TOKEN = "tokenaliceinrest";
	final String FB_MSG_URL = "https://graph.facebook.com/v2.6/me/messages?access_token="
							  + bc.getFbAccessToken();

	private HttpClient client = HttpClientBuilder.create().build();
	private HttpPost httppost = new HttpPost(FB_MSG_URL);
	
	
	@GET
	@Path("ask/{questao}")
	@Produces("application/json")
	@Consumes("application/json")
	@ApiOperation(value = "O chatterbot responde uma questão conforme a base de conhecimento", notes = "Robot em REST", response = Resposta.class)
	public Response consultar(@Context HttpServletRequest req,
							  @PathParam("questao") String questao) throws Exception {
		
		if (questao == null || "".equals(questao))
			throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
					.entity("Nenhuma questão informada.").build());

		Resposta r = null;
		try{
			String idCli;
			idCli = req.getRemoteHost();
			if (idCli == null)
				idCli = req.getRemoteAddr();
			r = bc.questionar(idCli, questao);
		}catch(Exception e)
		{
			e.printStackTrace();
			Resposta resp = new Resposta(questao, "Ocorreu um erro, favor recarregar a página para reiniciar.");
			return Response.ok().entity(resp).build();
		}
		return Response.ok().entity(r).build();
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

	
// Destinado ao facebook
	@POST
	@Path("webhook/")
	@Produces("application/json")
	@Consumes("application/json")
	@ApiOperation(value = "Webhook para receber mensagens do facebook", notes = "Para ser chamado pelo facebook")
	public Response fbwebhookPost(String str) throws Exception {
		System.out.println("requisição POST recebida: " + str);
		JsonElement jelement = new JsonParser().parse(str);
		JsonObject  body = jelement.getAsJsonObject();

		if (body == null || body.isJsonNull() || "".equals(str))
			throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
					.entity("Nenhuma informação.").build());
		if (!body.get("object").getAsString().equals("page"))
			return Response.status(Response.Status.NOT_FOUND).entity("EVENT_ERROR").build();
		
		JsonArray entry = null;
		if(body.get("entry").isJsonArray())
			entry = body.getAsJsonArray("entry");
		JsonArray mensagens = null;
		JsonObject o = entry.get(0).getAsJsonObject(); 
		//if(o.isJsonArray())
		mensagens = o.getAsJsonArray("messaging");
		
		JsonObject mensagem = mensagens.get(0).getAsJsonObject();
		JsonObject sender = mensagem.get("sender").getAsJsonObject();
		JsonObject objMensagem = mensagem.get("message").getAsJsonObject();
		String questao = objMensagem.get("text").getAsString();
		String cliente = sender.get("id").getAsString();
		
		marcarComoLida(cliente);
		sinalizarEscrevendo(cliente);
		
		FbMessage resposta = new FbMessage();
		String mensagemProcessada = this.responderParaFB(cliente,questao);
		resposta.setText(mensagemProcessada);
		
		sinalizarParouEscrever(cliente);
		
		enviarRespostaFB(cliente, resposta);
		
		
		return Response.ok().entity("EVENT_RECEIVED").build();
	}
	
	@GET
	@Path("webhook/")
	@Produces("application/json")
	@Consumes("application/json")
	@ApiOperation(value = "Responde a chamada do facebook com o código 200 caso seja a chama de uma página", notes = "Para ser chamado pelo facebook")
	public Response fbwebhookGet(@QueryParam("hub.mode") String mode,
								 @QueryParam("hub.verify_token") String verify_token,
								 @QueryParam("hub.challenge") String challenge
								) throws Exception {
		Hub hub = new Hub(mode,verify_token,challenge);
		System.out.println("requisição GET recebida: " + hub.toString());
		if ("".equals(hub.getMode()) || "".equals(hub.getVerify_token()))
			throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
					.entity("Erro nos parâmetros.").build());
		if (!hub.getMode().equals("subscribe") || !hub.getVerify_token().equals(VERIFY_TOKEN))
			return Response.status(Response.Status.NOT_FOUND).entity("EVENT_ERROR").build();
		String s = hub.getChallenge();
		Response r = javax.ws.rs.core.Response.ok(s).build();
		return r;
	}
	
	private void enviarRespostaFB(String senderId, FbMessage message) {

		FBMessageResponse envio = montaRespostaFB(senderId, message);
		String jsonEnvio = new Gson().toJson(envio);
		System.out.println("json de resposta: " + jsonEnvio);
		try {
			HttpEntity entity = new ByteArrayEntity(
					jsonEnvio.getBytes("UTF-8"));
			httppost.setEntity(entity);
			httppost.setHeader("Content-Type","application/json");
			HttpResponse response = client.execute(httppost);
			String result = EntityUtils.toString(response.getEntity());
			System.out.println("Enviou a resposta: " + message.getText());
			System.out.println("resultado do envio para o facebook:"+result);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private FBMessageResponse montaRespostaFB(String senderId, FbMessage message){
		Recipient recipient = new Recipient();
		recipient.setId(senderId);
		FBMessageResponse reply = new FBMessageResponse();
		reply.setRecipient(recipient);
		reply.setMessage(message);
		return reply;
	}
	
	private String responderParaFB(String cliente, String questao){
		Resposta r = null;
		try{
			r = bc.questionar(cliente, questao);
			return r.getConteudo();
		}catch(Exception e)
		{
			e.printStackTrace();
			return "Ocorreu um erro, favor tentar novamente mais tarde.";
		}
	}
	
	private void marcarComoLida(String mId){
		String s = "{\"recipient\":{ " +
			       "\"id\":\""+ mId +"\"}," +
			       "\"sender_action\":\"mark_seen\"}"; 

		try {
			HttpEntity entity = new ByteArrayEntity(
					s.getBytes("UTF-8"));
			httppost.setEntity(entity);
			httppost.setHeader("Content-Type","application/json");
			HttpResponse response = client.execute(httppost);
			String result = EntityUtils.toString(response.getEntity());
			System.out.println("Marcado como lida: " + s);
			System.out.println("resultado de marcado como lida:"+result);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void sinalizarEscrevendo(String mId){
		String s = "{\"recipient\":{ " +
			       "\"id\":\""+ mId +"\"}," +
			       "\"sender_action\":\"typing_on\"}"; 

		try {
			HttpEntity entity = new ByteArrayEntity(
					s.getBytes("UTF-8"));
			httppost.setEntity(entity);
			httppost.setHeader("Content-Type","application/json");
			HttpResponse response = client.execute(httppost);
			String result = EntityUtils.toString(response.getEntity());
			System.out.println("Marcado como escrevendo: " + s);
			System.out.println("resultado escrevendo:"+result);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void sinalizarParouEscrever(String mId){
		String s = "{\"recipient\":{ " +
			       "\"id\":\""+ mId +"\"}," +
			       "\"sender_action\":\"typing_off\"}"; 

		try {
			HttpEntity entity = new ByteArrayEntity(
					s.getBytes("UTF-8"));
			httppost.setEntity(entity);
			httppost.setHeader("Content-Type","application/json");
			HttpResponse response = client.execute(httppost);
			String result = EntityUtils.toString(response.getEntity());
			System.out.println("Marcado como parou de escrever: " + s);
			System.out.println("resultado parou de escrever:"+result);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
