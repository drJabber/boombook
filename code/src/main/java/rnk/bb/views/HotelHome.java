package rnk.bb.views;

import rnk.bb.domain.hotel.resource.Hotel;

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

    private List<Hotel> hotels;

    public void init(){
        log.log(Level.INFO,"Initializing boomHome...");
        selectAllHotels();
    }

    private void selectAllHotels(){
        this.hotels=findAllPublishdHotels();

    }
}
