package rnk.bb.auth.rest;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("v1")
public interface AuthIntf {
    @PUT
    @Path("auth")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    Response create(@BeanParam  JsonObject info) ;

    @POST
    @Path("auth")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    Response update(JsonObject info) ;

    @GET
    @Path("auth/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    Response read(@PathParam("login") String login) ;

    @DELETE
    @Path("auth/{login}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    Response delete(@PathParam("login") String login) ;

}
