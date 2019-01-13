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
    Response create(@Valid @BeanParam AuthInfo authInfo) throws InternalServerErrorException;

    @POST
    @Path("auth")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    Response update(@BeanParam AuthInfo authInfo) throws InternalServerErrorException;

    @GET
    @Path("auth/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    Response read(@PathParam("login") String login) throws InternalServerErrorException;

    @DELETE
    @Path("auth")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    Response delete(String login) throws InternalServerErrorException;

}
