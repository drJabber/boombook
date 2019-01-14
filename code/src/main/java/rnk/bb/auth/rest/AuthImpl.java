package rnk.bb.auth.rest;


import rnk.bb.auth.domain.Auth;
import rnk.bb.util.json.Util;

import javax.ejb.Singleton;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Path("/v1")
public class AuthImpl{

    @PersistenceContext(unitName="RNK_PU")
    private EntityManager em;

    @PUT
    @Path("auth")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
//    @Override
    public Response create(JsonObject info) {
        try{
            Auth auth=Util.unmarshal(info,Auth.class);
            em.persist(auth);
            return Response.ok().build();
        }catch(Exception ex){
            return Response.serverError().entity("cant parse query parameters").build();
        }
    }

    @POST
    @Path("auth")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
//    @Override
    public Response update(JsonObject info) {
        try{
            Auth auth=Util.unmarshal(info,Auth.class);
            em.merge(auth);
            return Response.ok().build();
        }catch(Exception ex){
            return Response.serverError().entity("cant parse query parameters").build();
        }
    }

    @GET
    @Path("/auth/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("login") String login) {
        Auth auth=em.find(Auth.class,login);
        if (auth!=null){
            return Response.ok().entity(auth).build();
        }else{
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("auth/{login}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response delete(@PathParam("login") String login) {
        Auth auth=em.find(Auth.class,login);
        if (auth!=null){
            em.remove(auth);
            return Response.ok().build();
        }else{
            return Response.serverError().build();
        }
    }
}
