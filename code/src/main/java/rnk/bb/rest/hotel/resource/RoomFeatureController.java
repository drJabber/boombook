package rnk.bb.rest.hotel.resource;

import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.domain.hotel.resource.RoomFeature;
import rnk.bb.rest.blank.CustomController;
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
public class RoomFeatureController extends CustomController<RoomFeature, Long> {

    public List<RoomFeature> createOrUpdateRoomFeatures(List<EditRoomFeatureBean> roomFeatureBeans, Hotel hotel){
        List<RoomFeature> list=new ArrayList<>();
        roomFeatureBeans.stream().forEach(rf->list.add(createOrUpdateRoomFeature(rf,hotel)));
        return list;
    }

    public RoomFeature createOrUpdateRoomFeature(EditRoomFeatureBean roomFeatureBean, Hotel hotel){
        RoomFeature rf=null;
        if (roomFeatureBean.getId()!=null){
            rf=findByLongId(roomFeatureBean.getId());
        }
        if (rf!=null){
            return updateRoomFeature(rf,roomFeatureBean,hotel);
        }else{
            return createRoomFeature(roomFeatureBean,hotel);
        }

    }

    public RoomFeature createRoomFeature(EditRoomFeatureBean roomFeatureBean, Hotel hotel){
        RoomFeature rf=new RoomFeature();
        return updateRoomFeature(rf,roomFeatureBean,hotel);
    }

    public RoomFeature updateRoomFeature(RoomFeature rf, EditRoomFeatureBean roomFeatureBean, Hotel hotel){
        rf.setHotel(hotel);
        rf.setPrice(roomFeatureBean.getPrice());
        rf.setName(roomFeatureBean.getName());
        rf.setDescription(roomFeatureBean.getDescription());
        return rf;
    }

    @PUT
    @Path("hotel/resource/rf")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(JsonObject info) {
        return saveInternal(info);
    }

    @POST
    @Path("hotel/resource/rf")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(JsonObject info) {
        return saveInternal(info);
    }

    @GET
    @Path("hotel/resource/rf/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") Long id) {
        return readInternal(id);
    }

    @DELETE
    @Path("hotel/resource/rf/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response delete(@PathParam("id") Long id) {
        return deleteInternal(id);
    }
}
