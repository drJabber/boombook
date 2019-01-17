package rnk.bb.rest.hotel.schedule;

import rnk.bb.domain.hotel.schedule.ScheduleItem;
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
public class ScheduleController {
    @PersistenceContext(unitName="RNK_PU")
    private EntityManager em;

    @PUT
    @Path("hotel/schedule")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createItem(JsonObject info) {
        try{
            ScheduleItem item= JsonHelper.unmarshal(info, ScheduleItem.class);
            em.persist(item);
            return Response.ok().entity(item).build();
        }catch(Exception ex){
            return Response.serverError().entity("cant parse query parameters").build();
        }
    }

    @POST
    @Path("hotel/schedule")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateItem(JsonObject info) {
        try{
            ScheduleItem item= JsonHelper.unmarshal(info,ScheduleItem.class);
            em.merge(item);
            return Response.ok().entity(item).build();
        }catch(Exception ex){
            return Response.serverError().entity("cant parse query parameters").build();
        }
    }

    @GET
    @Path("hotel/schedule/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readItem(@PathParam("id") Integer scheduleItemId) {
        ScheduleItem scheduleItem=em.find(ScheduleItem.class,scheduleItemId);
        if (scheduleItemId!=null){
            return Response.ok().entity(scheduleItem).build();
        }else{
            return Response.serverError().entity("cant find entity").build();
        }
    }

    @DELETE
    @Path("hotel/schedule/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response deleteItem(@PathParam("id") Integer scheduleItemId) {
        ScheduleItem scheduleItem=em.find(ScheduleItem.class,scheduleItemId);
        if (scheduleItem!=null){
            em.remove(scheduleItem);
            return Response.ok().build();
        }else{
            return Response.serverError().entity("cand find entity").build();
        }
    }
}
