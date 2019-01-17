package rnk.bb.rest.hotel.resource;

import rnk.bb.domain.hotel.resource.FoodConcept;
import rnk.bb.domain.hotel.resource.HotelPaymentPolicy;
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
public class FoodConceptController {
    @PersistenceContext(unitName="RNK_PU")
    private EntityManager em;

    @PUT
    @Path("hotel/resource/fc")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(JsonObject info) {
        try{
            FoodConcept concept = JsonHelper.unmarshal(info, FoodConcept.class);
            em.persist(concept);
            return Response.ok().entity(concept).build();
        }catch(Exception ex){
            return Response.serverError().entity("cant parse query parameters").build();
        }
    }

    @POST
    @Path("hotel/resource/fc")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(JsonObject info) {
        try{
            FoodConcept concept= JsonHelper.unmarshal(info, FoodConcept.class);
            em.merge(concept);
            return Response.ok().entity(concept).build();
        }catch(Exception ex){
            return Response.serverError().entity("cant parse query parameters").build();
        }
    }

    @GET
    @Path("hotel/resource/fc/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") Integer foodConceptId) {
        FoodConcept fc=em.find(FoodConcept.class,foodConceptId);
        if (fc!=null){
            return Response.ok().entity(fc).build();
        }else{
            return Response.serverError().entity("cant find entity").build();
        }
    }

    @DELETE
    @Path("hotel/resource/pp/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response delete(@PathParam("id") Integer foodConceptId) {
        FoodConcept fc=em.find(FoodConcept.class,foodConceptId);
        if (fc!=null){
            em.remove(fc);
            return Response.ok().build();
        }else{
            return Response.serverError().entity("cand find entity").build();
        }
    }
}
