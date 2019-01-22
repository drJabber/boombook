package rnk.bb.services;

import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.rest.hotel.resource.HotelController;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class HotelService implements Serializable {

    @Inject
    HotelController hotels;

    public Hotel findById(Long id){
        return this.hotels.findOptionalById(id).orElseThrow(()->new HotelNotFoundException(id));
    }

    public List<Hotel> findAllPublished(){
        return this.hotels.findAllPublished();
    }

}
