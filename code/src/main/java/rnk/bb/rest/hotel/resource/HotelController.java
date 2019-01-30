package rnk.bb.rest.hotel.resource;

import org.primefaces.model.LazyDataModel;
import rnk.bb.domain.hotel.resource.FoodConcept;
import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.rest.blank.CustomController;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.json.JsonObject;
import javax.persistence.criteria.*;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
@Startup
@DependsOn({"StartupController"})
@Path("v1")
public class HotelController  extends CustomController<Hotel, Long> {
    @PUT
    @Path("hotel/resource/hotel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(JsonObject info) {
        return saveInternal(info);
    }

    @POST
    @Path("hotel/resource/hotel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(JsonObject info) {
        return saveInternal(info);
    }

    @GET
    @Path("hotel/resource/hotel/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") Long foodConceptId) {
        return readInternal(foodConceptId);
    }

    @DELETE
    @Path("hotel/resource/hotel/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response delete(@PathParam("id") Long foodConceptId) {
        return deleteInternal(foodConceptId);
    }


    @PostConstruct
    public void init(){
        Map<String, Object> filter=new HashMap<>();
        filter.put("published",true);
        super.init(filter);
    }


    public List<FoodConcept> getFoodConceptList(Long hotelId){
        CriteriaBuilder cb = this.entityManager().getCriteriaBuilder();
        CriteriaQuery<FoodConcept> q = cb.createQuery(FoodConcept.class);
        Root<FoodConcept> root = q.from(FoodConcept.class);
        Join<FoodConcept,Hotel> hotelJoin=root.join("hotelId", JoinType.INNER);
        List<Predicate> predicates=new ArrayList<>();
        predicates.add(cb.equal(hotelJoin.get("hotelId"),hotelId));
        q.select(root).where(predicates.toArray(new Predicate[0]));

        return entityManager().createQuery(q).getResultList();
    }

}
