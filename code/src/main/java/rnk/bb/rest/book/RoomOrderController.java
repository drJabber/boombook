package rnk.bb.rest.book;

import rnk.bb.domain.book.Order;
import rnk.bb.domain.book.RoomOrder;
import rnk.bb.domain.hotel.resource.Guest;
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
public class RoomOrderController {
    @PersistenceContext(unitName="RNK_PU")
    private EntityManager em;

    @PUT
    @Path("book/ro")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(JsonObject info) {
        try{
            RoomOrder ro = JsonHelper.unmarshal(info, RoomOrder.class);
            em.persist(ro);
            return Response.ok().entity(ro).build();
        }catch(Exception ex){
            return Response.serverError().entity("cant parse query parameters").build();
        }
    }

    @POST
    @Path("book/ro")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(JsonObject info) {
        try{
            RoomOrder ro = JsonHelper.unmarshal(info, RoomOrder.class);
            em.merge(ro);
            return Response.ok().entity(ro).build();
        }catch(Exception ex){
            return Response.serverError().entity("cant parse query parameters").build();
        }
    }

    @GET
    @Path("book/ro/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") Integer roomOrderId) {
        RoomOrder ro=em.find(RoomOrder.class,roomOrderId);
        if (ro!=null){
            return Response.ok().entity(ro).build();
        }else{
            return Response.serverError().entity("cant find entity").build();
        }
    }

    @DELETE
    @Path("book/guest/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response delete(@PathParam("id") Integer roomOrderId) {
        RoomOrder ro=em.find(RoomOrder.class,roomOrderId);
        if (ro!=null){
            em.remove(ro);
            return Response.ok().build();
        }else{
            return Response.serverError().entity("cand find entity").build();
        }
    }
}
