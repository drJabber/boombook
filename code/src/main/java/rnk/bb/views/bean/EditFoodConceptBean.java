package rnk.bb.views.bean;

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
public class EditFoodConceptBean implements Serializable {
    private Long id=null;

    private Hotel hotel=null;

    @NotNull
    @Size(max=200)
    private String name;

    @NotNull
    @Min(0)
    private Double basePrice=0.0;

    @NotNull
    @Size(max=500)
    private String description;

    public String toString(){
        StringBuilder sb=new StringBuilder();
        List<String> list=new ArrayList<>();
        if (!name.trim().isEmpty()){
            ((ArrayList) list).add(name.trim());
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

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
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

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
