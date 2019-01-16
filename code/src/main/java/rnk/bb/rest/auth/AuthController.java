package rnk.bb.rest.auth;


import rnk.bb.domain.auth.Auth;
import rnk.bb.helper.json.JsonHelper;

import javax.ejb.DependsOn;
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
@DependsOn({"StartupController"})
@Path("/v1")
public class AuthController {

    @PersistenceContext(unitName="RNK_PU")
    private EntityManager em;

    @PUT
    @Path("auth")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response create(JsonObject info) {
        try{
            Auth auth= JsonHelper.unmarshal(info,Auth.class);
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
    public Response update(JsonObject info) {
        try{
            Auth auth= JsonHelper.unmarshal(info,Auth.class);
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
            return Response.serverError().entity("cant find entity").build();
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
            return Response.serverError().entity("cand find entity").build();
        }
    }
}
