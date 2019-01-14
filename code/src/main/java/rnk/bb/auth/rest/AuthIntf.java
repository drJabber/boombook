package rnk.bb.auth.rest;

import rnk.bb.auth.rest.bean.AuthInfo;

import javax.security.auth.login.LoginException;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("v1")
public interface AuthIntf {
    @PUT
    @Path("auth")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    Response create(@Valid @BeanParam AuthInfo authInfo) ;

    @POST
    @Path("auth")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    Response update(@Valid @BeanParam AuthInfo authInfo) ;

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
