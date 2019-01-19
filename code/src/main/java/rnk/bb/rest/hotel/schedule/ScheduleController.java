package rnk.bb.rest.hotel.schedule;

import rnk.bb.domain.hotel.schedule.ScheduleItem;
import rnk.bb.rest.blank.CustomController;

import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Startup
@DependsOn({"StartupController"})
@Path("v1")
public class ScheduleController  extends CustomController<ScheduleItem, Long> {
    @PUT
    @Path("hotel/schedule")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createItem(JsonObject info) {
        return saveInternal(info);
    }

    @POST
    @Path("hotel/schedule")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateItem(JsonObject info) {
        return saveInternal(info);
    }

    @GET
    @Path("hotel/schedule/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readItem(@PathParam("id") Long id) {
        return readInternal(id);
    }

    @DELETE
    @Path("hotel/schedule/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response deleteItem(@PathParam("id") Long id) {
        return deleteInternal(id);
    }
}
