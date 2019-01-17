package rnk.bb.rest.book;

import rnk.bb.domain.book.Order;
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
public class OrderController {
    @PersistenceContext(unitName="RNK_PU")
    private EntityManager em;

    @PUT
    @Path("book/order")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(JsonObject info) {
        try{
            Order order= JsonHelper.unmarshal(info, Order.class);
            em.persist(order);
            return Response.ok().entity(order).build();
        }catch(Exception ex){
            return Response.serverError().entity("cant parse query parameters").build();
        }
    }

    @POST
    @Path("book/order")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(JsonObject info) {
        try{
            Order order= JsonHelper.unmarshal(info, Order.class);
            em.merge(order);
            return Response.ok().entity(order).build();
        }catch(Exception ex){
            return Response.serverError().entity("cant parse query parameters").build();
        }
    }

    @GET
    @Path("book/order/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") Integer orderId) {
        Order order=em.find(Order.class,orderId);
        if (order!=null){
            return Response.ok().entity(order).build();
        }else{
            return Response.serverError().entity("cant find entity").build();
        }
    }

    @DELETE
    @Path("book/order/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response delete(@PathParam("id") Integer orderId) {
        Order order=em.find(Order.class,orderId);
        if (order!=null){
            em.remove(order);
            return Response.ok().build();
        }else{
            return Response.serverError().entity("cand find entity").build();
        }
    }
}
