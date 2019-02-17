package rnk.bb.rest.hotel.approval;

import rnk.bb.domain.hotel.approval.Approval;
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
public class ApprovalController  extends CustomController<Approval, Long> {

    @PUT
    @Path("hotel/approval/approval")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(JsonObject info) {
        return saveInternal(info);
    }

    @POST
    @Path("hotel/approval/approval")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(JsonObject info) {
        return saveInternal(info);
    }

    @GET
    @Path("hotel/approval/approval{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") Long approvalId) {
        return readInternal(approvalId);
    }

    @DELETE
    @Path("hotel/approval/approval{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response delete(@PathParam("id") Long approvalId) {
        return deleteInternal(approvalId);
    }
}

