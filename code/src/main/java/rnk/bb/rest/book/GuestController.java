package rnk.bb.rest.book;

import rnk.bb.domain.hotel.resource.Guest;
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
public class GuestController  extends CustomController<Guest, Long> {
    @PUT
    @Path("book/guest")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(JsonObject info) {
        return saveInternal(info);
    }

    @POST
    @Path("book/guest")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(JsonObject info) {
        return saveInternal(info);
    }

    @GET
    @Path("book/guest/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") Long guestId) {
        return readInternal(guestId);
    }

    @DELETE
    @Path("book/guest/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response delete(@PathParam("id") Long guestId) {
        return deleteInternal(guestId);
    }
}
