package rnk.bb.services;

public class HotelNotFoundException extends RuntimeException {
    public HotelNotFoundException(Long hotelId){
        super(String.format("Hotel id:%d not found",hotelId));
    }
}
