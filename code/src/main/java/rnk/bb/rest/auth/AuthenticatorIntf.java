package rnk.bb.rest.auth;

import rnk.bb.rest.auth.bean.LoginInfo;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public interface AuthenticatorIntf {
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    Response login(@BeanParam LoginInfo loginInfo, @Context HttpServletRequest request) throws LoginException;

    @POST
    @Path("/logout")
    Response logout(@Context HttpServletRequest request) throws ServletException;
}
