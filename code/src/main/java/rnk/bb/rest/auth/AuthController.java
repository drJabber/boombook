package rnk.bb.rest.auth;


import rnk.bb.domain.auth.Auth;
import rnk.bb.domain.auth.Role;
import rnk.bb.util.json.JsonHelper;

import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;

@Singleton
@Startup
@DependsOn({"StartupController"})
@Path("/v1")
public class AuthController {

    @PersistenceContext(unitName="RNK_PU")
    private EntityManager em;

    public Auth createAuth(JsonObject info) throws JAXBException {
        Auth auth= JsonHelper.unmarshal(info,Auth.class);
        em.persist(auth);
        return auth;
    }

    public Auth updateAuth(JsonObject info) throws JAXBException {
        Auth auth= JsonHelper.unmarshal(info,Auth.class);
        em.merge(auth);
        return auth;
    }

    public Auth getAuth(String login) {
        return em.find(Auth.class,login);
    }

    public Boolean deleteAuth(String login) {
        Auth auth=em.find(Auth.class,login);
        if (auth!=null) {
            em.remove(auth);
            return true;
        }
        return false;
    }

    @PUT
    @Path("auth")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(JsonObject info) {
        try{
            Auth auth=createAuth(info);
            return Response.ok().entity(auth).build();
        }catch(Exception ex){
            return Response.serverError().entity("cant parse query parameters").build();
        }
    }

    @POST
    @Path("auth")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(JsonObject info) {
        try{
            Auth auth=updateAuth(info);
            return Response.ok().entity(auth).build();
        }catch(Exception ex){
            return Response.serverError().entity("cant parse query parameters").build();
        }
    }

    @GET
    @Path("/auth/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("login") String login) {
        Auth auth=getAuth(login);
        if (auth!=null){
            return Response.ok().entity(auth).build();
        }else{
            return Response.serverError().entity("cant find entity").build();
        }
    }

    @GET
    @Path("/auth/role/{role}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRole(@PathParam("role") String role) {
        Role r=em.find(Role.class,role);
        if (r!=null){
            return Response.ok().entity(r).build();
        }else{
            return Response.serverError().entity("cant find entity").build();
        }
    }

    @DELETE
    @Path("auth/{login}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response delete(@PathParam("login") String login) {
        if (deleteAuth(login)){
            return Response.ok().build();
        }else{
            return Response.serverError().entity("cand find entity").build();
        }
    }
}
