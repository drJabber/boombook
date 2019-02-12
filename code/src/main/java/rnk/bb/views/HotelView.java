package rnk.bb.views;

import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.services.HotelService;
import rnk.bb.views.bean.hotel.EditHotelBean;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named("hotelView")
@ViewScoped
public class HotelView implements Serializable {

    private Hotel hotel;

    @Inject
    HotelService hotelService;

    @PostConstruct
    public void Init(){
        ExternalContext exContext= FacesContext.getCurrentInstance().getExternalContext();
        Long hotelId= Long.valueOf(exContext.getRequestParameterMap().get("id"));
        hotel=hotelService.findById(hotelId);
    }

    public Hotel getHotel(){
        return hotel;
    }
}
