package rnk.bb.views.bean.order;

import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.domain.util.Document;

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
public class EditRoomFeatureBean implements Serializable {
    private Long id=null;
    private Long fakeId;

    private Hotel hotel=null;


    @NotNull
    @Size(max=200)
    private String name="";

    @NotNull
    @Min(0)
    private Double price=0.0;

    @NotNull
    @Size(max=500)
    private String description="";

    public EditRoomFeatureBean(){
        this.fakeId=null;
    }

    public EditRoomFeatureBean(Long fakeId){
        this.fakeId=fakeId;
    }

    public String toString(){
        StringBuilder sb=new StringBuilder();
        List<String> list=new ArrayList<>();
        if (!name.trim().isEmpty()){
            ((ArrayList) list).add(name.trim());
        }

        if (price!=null){
            list.add(price.toString());
        }

        if (!description.trim().isEmpty()){
            list.add(description.trim());
        }

        return list.stream().collect(Collectors.joining(","));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFakeId() {
        return fakeId;
    }

    public void setFakeId(Long fakeId) {
        this.fakeId = fakeId;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
