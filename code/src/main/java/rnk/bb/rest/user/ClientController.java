package rnk.bb.rest.user;


import rnk.bb.domain.user.Client;
import rnk.bb.rest.user.bean.ClientInfo;
import rnk.bb.helper.json.JsonHelper;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Startup
@DependsOn({"rnk.bb.startup.rest.StartupController"})
@Path("v1")
public class ClientController {
    @PersistenceContext(unitName="RNK_PU")
    private EntityManager em;

    @PUT
    @Path("client")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response create(JsonObject info) {
        try{
            ClientInfo clientInfo= JsonHelper.unmarshal(info,ClientInfo.class);
	    Client client=createClient(clientInfo);
            em.persist(client);
            return Response.ok().build();
        }catch(Exception ex){
            return Response.serverError().entity("cant parse query parameters").build();
        }
    }

    private Client createClient(ClientInfo info){
    	Client client=new Client();
        client.setName(info.getName());
	client.setBirthDate(info.getBirthDate());
	client.setGender(info.getGender());
	
	Auth auth=em.find(info.getLogin(),Auth.class);
	client.setLogin(auth);
    }

    @POST
    @Path("client")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response update(JsonObject info) {
        try{
            ClientInfo client= JsonHelper.unmarshal(info,ClientInfo.class);
	    Client client=createClient(clientInfo);
            em.merge(client);
            return Response.ok().build();
        }catch(Exception ex){
            return Response.serverError().entity("cant parse query parameters").build();
        }
    }

    @GET
    @Path("/client/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") Integer clientId) {
        Client client=em.find(Client.class,clientId);
        if (client!=null){
            return Response.ok().entity(client).build();
        }else{
            return Response.serverError().entity("cant find entity").build();
        }
    }

    @DELETE
    @Path("client/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response delete(@PathParam("id") Integer clientId) {
        Client client=em.find(Client.class,clientId);
        if (client!=null){
            em.remove(client);
            return Response.ok().build();
        }else{
            return Response.serverError().entity("cand find entity").build();
        }
    }
}
