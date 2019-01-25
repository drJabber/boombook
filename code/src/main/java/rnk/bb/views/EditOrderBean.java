package rnk.bb.views;

import rnk.bb.domain.book.Order;
import rnk.bb.domain.book.RoomOrder;
import rnk.bb.domain.hotel.resource.Guest;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class EditOrderBean implements Serializable {
    private Long id=null;

    @Size(max=100)
    @Email(message = "Введите правильный email")
    private String email="";

    @NotNull
    @Size(max=100)
    private String phone="";

    private Boolean submitted=false;

    @Min(0)
    private Double price=0.0;

    private Boolean confirmed=false;

    private Boolean rejected=false;

    private List<RoomOrder> roomOrders=new ArrayList<>();

    @NotEmpty(message = "В заказе не указаны гости")
    private List<Guest> guests=new ArrayList<>();

    public void init(Order order){
        if (order!=null){
            this.id=order.getId();
            this.email=order.getEmail();
            this.phone=order.getPhone();
            this.price=order.getPrice();
            this.submitted= order.getSubmitted();
            this.confirmed=order.getConfirmed();
            this.rejected=order.getRejected();

        }
    }

    public void updateOrder(Order order){
        Order result=order;
        if (order==null){
            result=new Order();
        }
        result.setId(id);
        result.setPhone(phone);
        result.setEmail(email);
        result.setPrice(price);
        result.setConfirmed(confirmed);
        result.setSubmitted(submitted);
        result.setRejected(rejected);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Boolean submitted) {
        this.submitted = submitted;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Boolean getRejected() {
        return rejected;
    }

    public void setRejected(Boolean rejected) {
        this.rejected = rejected;
    }

    public List<RoomOrder> getRoomOrders() {
        return roomOrders;
    }

    public void setRoomOrders(List<RoomOrder> roomOrders) {
        this.roomOrders = roomOrders;
    }

    public List<Guest> getGuests() {
        return guests;
    }

    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }
}
