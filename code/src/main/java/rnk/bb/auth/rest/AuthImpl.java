package rnk.bb.auth.rest;


import rnk.bb.auth.domain.Auth;
import rnk.bb.auth.domain.Role;
import rnk.bb.auth.rest.bean.AuthInfo;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.Set;

@Singleton
@Path("v1")
public class AuthImpl implements AuthIntf {

    @PersistenceContext(unitName="RNK_PU")
    private EntityManager em;

    @PUT
    @Path("auth")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    @Override
    public Response create(@Valid @BeanParam AuthInfo authInfo) throws InternalServerErrorException {
        try {
            Auth auth=createAuth(authInfo);
            em.persist(auth);
            return Response.ok().build();
        }catch (Exception ex){
            throw new InternalServerErrorException("Cant register account") ;
        }
    }

    @POST
    @Path("auth")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    @Override
    public Response update(@BeanParam AuthInfo authInfo) throws InternalServerErrorException{
        try {
            Auth auth=createAuth(authInfo);
            if (auth!=null){
                em.merge(auth);
                return Response.ok().build();
            }else{
                return Response.serverError().build();
            }
        }catch (Exception ex){
            throw new InternalServerErrorException("Cant update account") ;
        }
    }

    @GET
    @Path("auth/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("login") String login) throws InternalServerErrorException{
        try {
            Auth auth=em.find(Auth.class,login);
            if (auth!=null){
                AuthInfo info =new AuthInfo(auth);
                return Response.ok().entity(info).build();
            }else{
                return Response.serverError().build();
            }
        }catch (Exception ex){
            throw new InternalServerErrorException("Cant find account") ;
        }
    }

    @DELETE
    @Path("auth")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response delete(String login) throws InternalServerErrorException{
        try {
            Auth auth=em.find(Auth.class,login);
            if (auth!=null){
                em.remove(auth);
                return Response.ok().build();
            }else{
                return Response.serverError().build();
            }
        }catch (Exception ex){
            throw new InternalServerErrorException("Cant delete account") ;
        }
    }

    private Auth createAuth(AuthInfo info){
        Auth auth=new Auth();
        auth.setLogin(info.getLogin());
        auth.setPassword(info.getPassword());
        auth.setEmail(info.getEmail());
        auth.setPhone(info.getPhone());
        auth.setRoles(createRoles(info));
        return auth;
    }

    private Set<Role> createRoles(AuthInfo info){
        Set<Role> roles=new HashSet<>();
        info.getRoles().stream().forEach(r->roles.add(new Role(r)));
        return roles;
    }
}
