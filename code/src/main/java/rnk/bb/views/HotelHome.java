package rnk.bb.views;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;
import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.services.HotelService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("boomHome")
@SessionScoped
//@ViewScoped()
public class HotelHome implements Serializable {
    private static Logger log=Logger.getLogger(HotelHome.class.getName());

    @Inject
    HotelService hotelService;

    private LazyDataModel<Hotel> hotelsModel;
    private Hotel selectedHotel;

    @PostConstruct
    public void init(){
        log.log(Level.INFO,"boomHome startup...");
        this.hotelsModel=hotelService.getController();
    }

    public LazyDataModel<Hotel> getHotelsModel(){
        return hotelsModel;
    }

    public Hotel getSelectedHotel(){
        return selectedHotel;
    }

    public void setSelectedHotel(Hotel hotel){
        this.selectedHotel=hotel;
    }

    public void onRowSelect(SelectEvent event) throws IOException {
        Hotel hotel=(Hotel)event.getObject();
        log.log(Level.INFO,String.format("Hotel selected %s", hotel.getName()));
        FacesContext.getCurrentInstance().getExternalContext().redirect("hotel.xhtml");
    }


}
