package rnk.bb.views.bean.order;

import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.domain.util.Document;
import rnk.bb.services.bean.RoomTypeBean;

import javax.enterprise.context.SessionScoped;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@SessionScoped
public class EditRoomPoolBean implements Serializable {
    private Long id=null;

    private Hotel hotel=null;

    private RoomTypeBean roomType=new RoomTypeBean();

    @NotNull
    private String name="";

    @NotNull
    @Min(0)
    private Double basePrice=0.0;

    public String toString(){
        return name;
    }

    public Hotel getHotel(){
        return hotel;
    }

    public void setHotel(Hotel hotel){
        this.hotel=hotel;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id=id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }

    public RoomTypeBean getRoomType(){
        return roomType;
    }

    public void setRoomType(RoomTypeBean roomType){
        this.roomType=roomType;
    }


    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }
}
