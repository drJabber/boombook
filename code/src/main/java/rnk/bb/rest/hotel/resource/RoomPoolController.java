package rnk.bb.rest.hotel.resource;

import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.domain.hotel.resource.RoomPool;
import rnk.bb.rest.blank.CustomController;
import rnk.bb.views.bean.hotel.EditRoomPoolBean;

import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
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
public class RoomPoolController extends CustomController<RoomPool, Long> {

    @Inject
    RoomTypeController roomTypes;

    public List<RoomPool> createOrUpdateRoomPools(List<EditRoomPoolBean> roomPoolBeans, Hotel hotel){
        List<RoomPool> list=new ArrayList<>();
        roomPoolBeans.stream().forEach(rp->list.add(createOrUpdateRoomPool(rp,hotel)));
        return list;
    }

    public RoomPool createOrUpdateRoomPool(EditRoomPoolBean roomPoolBean, Hotel hotel){
        RoomPool rp=null;
        if (roomPoolBean.getId()!=null){
            rp=findByLongId(roomPoolBean.getId());
        }
        if (rp!=null){
            return updateRoomPool(rp,roomPoolBean,hotel);
        }else{
            return createRoomPool(roomPoolBean,hotel);
        }

    }

    public RoomPool createRoomPool(EditRoomPoolBean roomPoolBean, Hotel hotel){
        RoomPool rp=new RoomPool();
        return updateRoomPool(rp,roomPoolBean,hotel);
    }

    public RoomPool updateRoomPool(RoomPool rp, EditRoomPoolBean roomPoolBean, Hotel hotel){
        rp.setHotel(hotel);
        rp.setBasePrice(roomPoolBean.getBasePrice());
        rp.setName(roomPoolBean.getName());
        rp.setRoomType(roomTypes.findByLongId(roomPoolBean.getRoomType().getId()));
        return rp;
    }

    @PUT
    @Path("hotel/resource/rp")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(JsonObject info) {
        return saveInternal(info);
    }

    @POST
    @Path("hotel/resource/rp")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(JsonObject info) {
        return saveInternal(info);
    }

    @GET
    @Path("hotel/resource/rp/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") Long id) {
        return readInternal(id);
    }

    @DELETE
    @Path("hotel/resource/rp/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response delete(@PathParam("id") Long id) {
        return deleteInternal(id);
    }
}
