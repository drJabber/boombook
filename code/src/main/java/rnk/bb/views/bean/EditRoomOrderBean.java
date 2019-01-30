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
public class EditRoomOrderBean implements Serializable {
    private Long id=null;

    private Order order=null;

    @NotNull
    private EditRoomPoolBean roomPool=new EditRoomPoolBean();

    private checkInDate=null;
    private checkOutDate=null;

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

    public EditRoomPoolBean getRoomPoolBean(){
        return roomPool;
    }

    public void setRoomPoolBean(EditRoomPoolBean roomPool){
        this.roomPool=roomPool;
    }

    public Date getCheckInDate(){
        return checkInDate;
    }

    public void setCheckInDate(Date date){
        this.checkInDate=date;
    }

    public Date getCheckOutDate(){
        return checkOutDate;
    }

    public void setCheckOutDate(Date date){
        this.checkOutDate=date;
    }
    
}
