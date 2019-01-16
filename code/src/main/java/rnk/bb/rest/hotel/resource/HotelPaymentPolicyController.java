package rnk.bb.rest.hotel.resource;

import rnk.bb.domain.hotel.resource.HotelPaymentPolicy;
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
public class HotelPaymentPolicyController {
    @PersistenceContext(unitName="RNK_PU")
    private EntityManager em;

    @PUT
    @Path("hotel/resource/pp")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response create(JsonObject info) {
        try{
            HotelPaymentPolicy ploicy= JsonHelper.unmarshal(info, HotelPaymentPolicy.class);
            em.persist(ploicy);
            return Response.ok().build();
        }catch(Exception ex){
            return Response.serverError().entity("cant parse query parameters").build();
        }
    }

    @POST
    @Path("hotel/resource/pp")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response update(JsonObject info) {
        try{
            HotelPaymentPolicy ploicy= JsonHelper.unmarshal(info, HotelPaymentPolicy.class);
            em.merge(ploicy);
            return Response.ok().build();
        }catch(Exception ex){
            return Response.serverError().entity("cant parse query parameters").build();
        }
    }

    @GET
    @Path("hotel/resource/pp/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") Integer policyId) {
        HotelPaymentPolicy policy=em.find(HotelPaymentPolicy.class,policyId);
        if (policy!=null){
            return Response.ok().entity(policy).build();
        }else{
            return Response.serverError().entity("cant find entity").build();
        }
    }

    @DELETE
    @Path("hotel/resource/pp/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response delete(@PathParam("id") Integer policyId) {
        HotelPaymentPolicy policy=em.find(HotelPaymentPolicy.class,policyId);
        if (policy!=null){
            em.remove(policy);
            return Response.ok().build();
        }else{
            return Response.serverError().entity("cand find entity").build();
        }
    }
}
