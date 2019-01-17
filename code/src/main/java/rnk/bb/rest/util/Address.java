package rnk.bb.rest.util;

import rnk.bb.domain.auth.Role;
import rnk.bb.domain.util.Country;
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
@Path("v1")
public class Address {
    @PersistenceContext(unitName="RNK_PU")
    private EntityManager em;

    @PUT
    @Path("util/adr")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(JsonObject info) {
        try{
            Address address= JsonHelper.unmarshal(info, Address.class);
            em.persist(address);
            return Response.ok().entity(address).build();
        }catch(Exception ex){
            return Response.serverError().entity("cant parse query parameters").build();
        }
    }

    @POST
    @Path("util/adr")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(JsonObject info) {
        try{
            Address address=JsonHelper.unmarshal(info,Address.class);
            em.merge(address);
            return Response.ok().entity(address).build();
        }catch(Exception ex){
            return Response.serverError().entity("cant parse query parameters").build();
        }
    }

    @GET
    @Path("util/adr/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") Integer addressId) {
        Address address=em.find(Address.class,addressId);
        if (address!=null){
            return Response.ok().entity(address).build();
        }else{
            return Response.serverError().entity("cant find entity").build();
        }
    }

    @GET
    @Path("/util/country/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCountry(@PathParam("id") Integer countryId) {
        Country country=em.find(Country.class,countryId);
        if (country!=null){
            return Response.ok().entity(country).build();
        }else{
            return Response.serverError().entity("cant find entity").build();
        }
    }

    @DELETE
    @Path("util/adr/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response delete(@PathParam("id") Integer addressId) {
        Address address=em.find(Address.class,addressId);
        if (address!=null){
            em.remove(address);
            return Response.ok().build();
        }else{
            return Response.serverError().entity("cand find entity").build();
        }
    }
}
