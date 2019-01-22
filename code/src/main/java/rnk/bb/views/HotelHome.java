package rnk.bb.views;

import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.services.HotelService;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("boomHome")
@ViewScoped()
public class HotelHome implements Serializable {
    @Inject
    Logger log;

    @Inject
    HotelService hotelService;

    private List<Hotel> hotels;

    public void init(){
        log.log(Level.INFO,"boomHome startup...");
        selectAllHotels();
    }

    private void selectAllHotels(){
        log.log(Level.INFO,"Extracting hotels...");
        this.hotels=findAllPublishedHotels();
    }

    private List<Hotel> findAllPublishedHotels(){
        return hotelService.findAllPublished();
    }
}
