package rnk.bb.rest.hotel.resource;


import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.domain.hotel.staff.Staff;
import rnk.bb.domain.user.Client;
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
public class HotelController {
    @PersistenceContext(unitName="RNK_PU")
    private EntityManager em;

    @PUT
    @Path("hotel/resource/hotel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response create(JsonObject info) {
        try{
            Hotel hotel= JsonHelper.unmarshal(info,Hotel.class);
            em.persist(hotel);
            return Response.ok().build();
        }catch(Exception ex){
            return Response.serverError().entity("cant parse query parameters").build();
        }
    }

    @POST
    @Path("hotel/resource/hotel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response update(JsonObject info) {
        try{
            Hotel hotel= JsonHelper.unmarshal(info,Hotel.class);
            em.merge(hotel);
            return Response.ok().build();
        }catch(Exception ex){
            return Response.serverError().entity("cant parse query parameters").build();
        }
    }

    @GET
    @Path("hotel/resource/hotel/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") Integer hotelId) {
        Hotel hotel=em.find(Hotel.class,hotelId);
        if (hotel!=null){
            return Response.ok().entity(hotel).build();
        }else{
            return Response.serverError().entity("cant find entity").build();
        }
    }

    @DELETE
    @Path("hotel/resource/hotel/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response delete(@PathParam("id") Integer hotelId) {
        Hotel hotel=em.find(Hotel.class,hotelId);
        if (hotel!=null){
            em.remove(hotel);
            return Response.ok().build();
        }else{
            return Response.serverError().entity("cand find entity").build();
        }
    }

}
