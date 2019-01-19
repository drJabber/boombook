package rnk.bb.rest.hotel.staff;


import rnk.bb.domain.hotel.staff.Staff;
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
public class StaffController extends CustomController<Staff, Long> {
    @PUT
    @Path("hotel/staff")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(JsonObject info) {
        return saveInternal(info);
    }

    @POST
    @Path("hotel/staff")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(JsonObject info) {
        return saveInternal(info);
    }

    @GET
    @Path("hotel/staff/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") Long staffId) {
        return readInternal(staffId);
    }

    @DELETE
    @Path("hotel/staff/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response delete(@PathParam("id") Long staffId) {
        return deleteInternal(staffId);
    }
}
