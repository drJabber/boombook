package rnk.bb.views;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.services.HotelService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("boomHome")
@ViewScoped()
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

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Hotel selected", ((Hotel) event.getObject()).getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }}
