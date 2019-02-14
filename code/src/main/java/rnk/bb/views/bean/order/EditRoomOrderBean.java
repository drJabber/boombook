package rnk.bb.views.bean.order;

import rnk.bb.domain.book.Order;
import rnk.bb.views.bean.hotel.EditRoomFeatureBean;
import rnk.bb.views.bean.hotel.EditRoomPoolBean;

import javax.enterprise.context.SessionScoped;
import javax.validation.constraints.NotNull;
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

    private Date checkInDate=null;
    private Date checkOutDate=null;

    private List<EditRoomFeatureBean> features=new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public EditRoomPoolBean getRoomPool() {
        return roomPool;
    }

    public void setRoomPool(EditRoomPoolBean roomPool) {
        this.roomPool = roomPool;
    }

    public String toString(){
        SimpleDateFormat df=new SimpleDateFormat("dd.MM.yyyy");
        StringBuilder sb=new StringBuilder();
        List<String> list=new ArrayList<>();
        if (roomPool!=null){
            if (!roomPool.getName().trim().isEmpty()){
                ((ArrayList) list).add(roomPool.getName().trim());
            }

            if (roomPool.getRoomType()!=null){
                list.add("("+roomPool.getRoomType().toString()+")");
            }
        }

        if (checkInDate!=null){
            list.add(df.format(checkInDate));
        }

        if (checkOutDate!=null){
            list.add(df.format(checkOutDate));
        }

        return list.stream().collect(Collectors.joining(","));
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
    
   public List<EditRoomFeatureBean> getFeatures() {
        return features;
    }

    public void setFeatures(List<EditRoomFeatureBean> features) {
        this.features = features;
    }
    
}
