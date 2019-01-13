package rnk.bb.auth.rest;

import rnk.bb.auth.rest.bean.LoginInfo;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;


public class Authenticator implements AuthenticatorIntf {
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    @Override
    public Response login(LoginInfo loginInfo, @Context HttpServletRequest request) throws LoginException {
        try {
            request.login(loginInfo.getLogin(),loginInfo.getPassword());
            return Response.ok().build();
        }catch (Exception ex){
            throw new LoginException("Cant login application") ;
        }
    }

    @POST
    @Path("/logout")
    @Produces(MediaType.TEXT_HTML)
    @Override
    public Response logout(@Context HttpServletRequest request) throws ServletException {
        request.logout();
        return Response.ok().build();

    }
}
