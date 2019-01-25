package rnk.bb.views;

import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.rest.hotel.resource.HotelController;
import rnk.bb.services.HotelService;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named("hotelView")
@ViewScoped()
public class HotelView implements Serializable {

    private Hotel hotel;

    @Inject
    HotelService hotelService;

    @PostConstruct
    public void init(){

        Long hotelId= Long.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
        hotel=hotelService.findById(hotelId);
    }

    public Hotel getHotel(){
        return hotel;
    }
}
