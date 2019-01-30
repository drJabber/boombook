package rnk.bb.views.bean;

import rnk.bb.domain.book.Order;
import rnk.bb.domain.book.RoomOrder;
import rnk.bb.domain.hotel.resource.Guest;
import rnk.bb.domain.user.Client;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named("editOrderBean")
@SessionScoped
public class EditOrderBean implements Serializable {
    private Client client;

    private Long id=null;

    @Size(max=100)
//    @Email(message = "Введите правильный email")
    private String email="";

    @NotNull
    @Size(max=100)
    private String phone="";

    private Boolean submitted=false;

    @Min(0)
    private Double price=0.0;

    private Boolean confirmed=false;

    private Boolean rejected=false;

    Date checkInTime=null;

    Date checkOutTime=null;

    
    private List<RoomOrder> roomOrders=new ArrayList<>();

    @NotEmpty(message = "В заказе не указаны гости")
    private List<EditGuestBean> guests=new ArrayList<>();

    @NotEmpty(message = "В заказе не указаны комнаты")
    private List<EditRoomBean> rooms=new ArrayList<>();

    public EditOrderBean(){

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

    public List<EditGuestBean> getGuests() {
        return guests;
    }

    public void setGuests(List<EditGuestBean> guests) {
        this.guests = guests;
    }

    public void setRooms(List<EditRoomBean> rooms) {
        this.guests = rooms;
    }

    public List<EditRoomBean> getRooms() {
        return rooms;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Date date) {
        this.checkInTime = date;
    }

    public Date getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Date date) {
        this.checkOutTime = date;
    }
}
