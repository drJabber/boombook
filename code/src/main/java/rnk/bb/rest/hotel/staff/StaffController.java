package rnk.bb.rest.hotel.staff;


import rnk.bb.domain.hotel.staff.Staff;
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
public class StaffController {
    @PersistenceContext(unitName="RNK_PU")
    private EntityManager em;

    @PUT
    @Path("hotel/staff")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response create(JsonObject info) {
        try{
            Staff staff= JsonHelper.unmarshal(info,Staff.class);
            em.persist(staff);
            return Response.ok().build();
        }catch(Exception ex){
            return Response.serverError().entity("cant parse query parameters").build();
        }
    }

    @POST
    @Path("hotel/staff")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response update(JsonObject info) {
        try{
            Staff staff= JsonHelper.unmarshal(info,Staff.class);
            em.merge(staff);
            return Response.ok().build();
        }catch(Exception ex){
            return Response.serverError().entity("cant parse query parameters").build();
        }
    }

    @GET
    @Path("hotel/staff/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") Integer staffId) {
        Staff staff=em.find(Staff.class,staffId);
        if (staff!=null){
            return Response.ok().entity(staff).build();
        }else{
            return Response.serverError().entity("cant find entity").build();
        }
    }

    @DELETE
    @Path("hotel/staff/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response delete(@PathParam("id") Integer staffId) {
        Staff staff=em.find(Staff.class,staffId);
        if (staff!=null){
            em.remove(staff);
            return Response.ok().build();
        }else{
            return Response.serverError().entity("cand find entity").build();
        }
    }
}
