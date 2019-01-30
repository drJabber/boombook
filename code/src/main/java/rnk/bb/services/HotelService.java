package rnk.bb.services;

import org.primefaces.model.LazyDataModel;
import rnk.bb.domain.hotel.resource.FoodConcept;
import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.rest.hotel.resource.FoodConceptController;
import rnk.bb.rest.hotel.resource.HotelController;
import rnk.bb.views.bean.EditDocumentBean;
import rnk.bb.views.bean.EditFoodConceptBean;
import rnk.bb.views.bean.EditHotelBean;

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

}
