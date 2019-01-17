package rnk.bb.rest.hotel.resource;

import rnk.bb.domain.hotel.resource.RoomFeature;
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
public class RoomFeatureController {
    @PersistenceContext(unitName="RNK_PU")
    private EntityManager em;

    @PUT
    @Path("hotel/resource/rf")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(JsonObject info) {
        try{
            RoomFeature feature = JsonHelper.unmarshal(info, RoomFeature.class);
            em.persist(feature);
            return Response.ok().entity(feature).build();
        }catch(Exception ex){
            return Response.serverError().entity("cant parse query parameters").build();
        }
    }

    @POST
    @Path("hotel/resource/rf")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(JsonObject info) {
        try{
            RoomFeature feature= JsonHelper.unmarshal(info, RoomFeature.class);
            em.merge(feature);
            return Response.ok().entity(feature).build();
        }catch(Exception ex){
            return Response.serverError().entity("cant parse query parameters").build();
        }
    }

    @GET
    @Path("hotel/resource/rf/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") Integer roomFeatureId) {
        RoomFeature rf=em.find(RoomFeature.class,roomFeatureId);
        if (rf!=null){
            return Response.ok().entity(rf).build();
        }else{
            return Response.serverError().entity("cant find entity").build();
        }
    }

    @DELETE
    @Path("hotel/resource/pp/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response delete(@PathParam("id") Integer roomFeatureId) {
        RoomFeature rf=em.find(RoomFeature.class,roomFeatureId);
        if (rf!=null){
            em.remove(rf);
            return Response.ok().build();
        }else{
            return Response.serverError().entity("cand find entity").build();
        }
    }
}
