package rnk.bb.services;

import org.primefaces.model.LazyDataModel;
import rnk.bb.domain.hotel.resource.FoodConcept;
import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.domain.hotel.resource.RoomFeature;
import rnk.bb.domain.hotel.resource.RoomPool;
import rnk.bb.rest.hotel.resource.FoodConceptController;
import rnk.bb.rest.hotel.resource.HotelController;
import rnk.bb.rest.hotel.resource.RoomFeatureController;
import rnk.bb.rest.hotel.resource.RoomPoolController;
import rnk.bb.services.bean.RoomTypeBean;
import rnk.bb.views.bean.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class HotelService implements Serializable {

    @Inject
    HotelController hotels;

    @Inject
    FoodConceptController foodConcepts;

    @Inject
    CacheService cacheService;

    @Inject
    RoomPoolController roomPools;

    @Inject
    RoomFeatureController roomFeatures;

    public Hotel findById(Long id){
        return this.hotels.findOptionalById(id).orElseThrow(()->new HotelNotFoundException(id));
    }

    public LazyDataModel<Hotel> getController(){
        return this.hotels;
    }


    private EditHotelBean cleanHotelBean(EditHotelBean hotelBean){
        hotelBean.setId(null);
        hotelBean.setName("");
        hotelBean.getFoodConcepts().clear();
        return hotelBean;
    }

    public EditHotelBean initHotelBean(EditHotelBean hotelBean, Long hotelId){
        Hotel hotel =null;
        if (hotelId!=null){
            hotel=hotels.findOptionalById(hotelId).orElse(null);
        }
        return initHotelBean(hotelBean,hotel);
    }

    public EditHotelBean initHotelBean(EditHotelBean hotelBean, Hotel hotel){
        if (hotel!=null) {
            hotelBean.setId(hotel.getId());
            hotelBean.setName(hotel.getName());

            List<EditFoodConceptBean> list=hotelBean.getFoodConcepts();
            list.clear();
            hotel.getFoodConcepts().stream().forEach(fc->list.add(initFoodConceptBean(new EditFoodConceptBean(),fc)));

            List<EditRoomFeatureBean> rflist=hotelBean.getRoomFeatures();
            rflist.clear();
            hotel.getRoomFeatures().stream().forEach(rf->rflist.add(initRoomFeatureBean(new EditRoomFeatureBean(),rf)));

            return hotelBean;
        }else{
            return cleanHotelBean(hotelBean);
        }
    }

    private EditFoodConceptBean cleanFoodConceptBean(EditFoodConceptBean fcBean){
        fcBean.setId(null);
        fcBean.setHotel(null);
        fcBean.setBasePrice(null);
        fcBean.setDescription("");
        fcBean.setName("");

        return fcBean;
    }

    public EditFoodConceptBean initFoodConceptBean(EditFoodConceptBean fcBean, FoodConcept fc){
        if (fc!=null) {
            fcBean.setId(fc.getId());
            fcBean.setHotel(fc.getHotel());
            fcBean.setName(fc.getName());
            fcBean.setBasePrice(fc.getBasePrice());
            fcBean.setDescription(fc.getDescription());
            return fcBean;
        }else{
            return cleanFoodConceptBean(fcBean);
        }
    }

    public EditFoodConceptBean initFoodConceptBean(EditFoodConceptBean fcBean, EditFoodConceptBean anotherBean){
        if ((fcBean!=null)&&(anotherBean!=null)) {
            FoodConcept foodConcept=foodConcepts.findByLongId(anotherBean.getId());

            fcBean.setHotel(foodConcept.getHotel());
            fcBean.setId(foodConcept.getId());
            fcBean.setName(foodConcept.getName());
            fcBean.setBasePrice(foodConcept.getBasePrice());
            fcBean.setDescription(foodConcept.getDescription());
            return fcBean;
        }else{
            return cleanFoodConceptBean(fcBean);
        }
    }

    private EditRoomPoolBean cleanRoomPoolBean(EditRoomPoolBean rpBean){
        rpBean.setId(null);
        rpBean.setHotel(null);
        rpBean.setName("");
        rpBean.setBasePrice(null);
        rpBean.setRoomType(null);
        return rpBean;
    }

    public EditRoomPoolBean initRoomPoolBean(EditRoomPoolBean rpBean, RoomPool rp){
        if (rp!=null) {
            rpBean.setId(rp.getId());
            rpBean.setHotel(rp.getHotel());
            rpBean.setName(rp.getName());
            rpBean.setBasePrice(rp.getBasePrice());

            rpBean.setRoomType(cacheService.getRoomTypeById(rp.getRoomType().getName()));
            return rpBean;
        }else{
            return cleanRoomPoolBean(rpBean);
        }
    }

    public EditRoomPoolBean initRoomPoolBean(EditRoomPoolBean rpBean, EditRoomPoolBean anotherBean){
        if ((rpBean!=null)&&(anotherBean!=null)) {
            RoomPool roomPool=roomPools.findByLongId(anotherBean.getId());

            rpBean.setId(roomPool.getId());
            rpBean.setHotel(roomPool.getHotel());
            rpBean.setName(roomPool.getName());
            rpBean.setBasePrice(roomPool.getBasePrice());

            rpBean.setRoomType(anotherBean.getRoomType());

            return rpBean;
        }else{
            return cleanRoomPoolBean(rpBean);
        }
    }


    private EditRoomFeatureBean cleanRoomFeatureBean(EditRoomFeatureBean rfBean){
        rfBean.setId(null);
        rfBean.setHotel(null);
        rfBean.setPrice(null);
        rfBean.setDescription("");
        rfBean.setName("");

        return rfBean;
    }

    public EditRoomFeatureBean initRoomFeatureBean(EditRoomFeatureBean rfBean, RoomFeature rf){
        if (rf!=null) {
            rfBean.setId(rf.getId());
            rfBean.setHotel(rf.getHotel());
            rfBean.setName(rf.getName());
            rfBean.setPrice(rf.getPrice());
            rfBean.setDescription(rf.getDescription());
            return rfBean;
        }else{
            return cleanRoomFeatureBean(rfBean);
        }
    }

    public EditRoomFeatureBean initRoomFeatureBean(EditRoomFeatureBean rfBean, EditRoomFeatureBean anotherBean){
        if ((rfBean!=null)&&(anotherBean!=null)) {
            RoomFeature rf=roomFeatures.findByLongId(anotherBean.getId());

            rfBean.setHotel(rf.getHotel());
            rfBean.setId(rf.getId());
            rfBean.setName(rf.getName());
            rfBean.setPrice(rf.getPrice());
            rfBean.setDescription(rf.getDescription());
            return rfBean;
        }else{
            return cleanRoomFeatureBean(rfBean);
        }
    }

    
}
