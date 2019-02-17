package rnk.bb.rest.hotel.resource;

import rnk.bb.domain.hotel.resource.FoodConcept;
import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.rest.blank.CustomController;
import rnk.bb.views.bean.hotel.EditFoodConceptBean;
import rnk.bb.views.bean.hotel.EditRoomFeatureBean;

import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Singleton
@Startup
@DependsOn({"StartupController"})
@Path("v1")
public class FoodConceptController  extends CustomController<FoodConcept, Long> {

    public List<FoodConcept> createOrUpdateFoodConcepts(List<EditFoodConceptBean> foodConceptBeans, Hotel hotel){
        List<FoodConcept> list=new ArrayList<>();
        foodConceptBeans.stream().forEach(fc->list.add(createOrUpdateFoodConcept(fc,hotel)));
        return list;
    }

    public FoodConcept createOrUpdateFoodConcept(EditFoodConceptBean foodConceptBean, Hotel hotel){
        FoodConcept fc=null;
        if (foodConceptBean.getId()!=null){
            fc=findByLongId(foodConceptBean.getId());
        }
        if (fc!=null){
            return updateFoodConcept(fc,foodConceptBean,hotel);
        }else{
            return createFoodConcept(foodConceptBean,hotel);
        }

    }

    public FoodConcept createFoodConcept(EditFoodConceptBean foodConceptBean, Hotel hotel){
        FoodConcept fc=new FoodConcept();
        return updateFoodConcept(fc,foodConceptBean,hotel);
    }

    public FoodConcept updateFoodConcept(FoodConcept fc, EditFoodConceptBean foodConceptBean, Hotel hotel){
        fc.setHotel(hotel);
        fc.setBasePrice(foodConceptBean.getBasePrice());
        fc.setName(foodConceptBean.getName());
        fc.setDescription(foodConceptBean.getDescription());
        return fc;
    }

    @PUT
    @Path("hotel/resource/fc")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(JsonObject info) {
        return saveInternal(info);
    }

    @POST
    @Path("hotel/resource/fc")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(JsonObject info) {
        return saveInternal(info);
    }

    @GET
    @Path("hotel/resource/fc/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") Long foodConceptId) {
        return readInternal(foodConceptId);
    }

    @DELETE
    @Path("hotel/resource/fc/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response delete(@PathParam("id") Long foodConceptId) {
        return deleteInternal(foodConceptId);
    }
}
