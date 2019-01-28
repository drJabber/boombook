package rnk.bb.views;

import org.primefaces.event.SelectEvent;
import rnk.bb.domain.book.Order;
import rnk.bb.domain.hotel.resource.Guest;
import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.rest.book.GuestController;
import rnk.bb.rest.book.OrderController;
import rnk.bb.services.HotelService;
import rnk.bb.views.bean.EditGuestBean;
import rnk.bb.views.bean.EditOrderBean;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("editOrderView")
@SessionScoped
public class EditOrderView implements Serializable {
    private static Logger log=Logger.getLogger(EditOrderView.class.getName());

    private Long orderId=null;
    private Long guestId=null;

    private Hotel hotel=null;
    private Order order=null;
    private Guest guest=null;

    private String state="order";

    @Inject
    OrderController orderController;

    @Inject
    GuestController guestController;

    @Inject
    HotelService hotelService;

//    @Inject
    EditOrderBean orderBean;

//    @Inject
    EditGuestBean guestBean;

    @PostConstruct
    public void init(){
        Map<String, String> params= FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        if (params.get("hotelId")!=null){
            Long hotelId= Long.valueOf(params.get("hotelId"));
            hotel=hotelService.findById(hotelId);
        }

        if (params.get("orderId")!=null){
            this.orderId=Long.valueOf(params.get("orderId"));
            order=this.orderController.findOptionalById(orderId).orElse(null);
        }

        if (params.get("guestId")!=null){
            this.guestId=Long.valueOf(params.get("guestId"));
            guest=this.guestController.findOptionalById(guestId).orElse(null);
        }

        orderBean=new EditOrderBean();
        orderBean.init(order);

        guestBean=new EditGuestBean();
        guestBean.init(guest);
    }


    public Hotel getHotel(){
        return hotel;
    }

    public EditOrderBean getOrderBean(){
        return orderBean;
    }

    public EditGuestBean getGuestBean(){
        return guestBean;
    }

    public void setGuestBean(EditGuestBean guestBean){
        this.guestBean=guestBean;
    }

    public String getState(){
        return state;
    }

    public void addGuest(){
        log.log(Level.INFO,"add new guest");
        guestBean.init(null);
        state="guest";
    }

    public void saveGuest(EditGuestBean guestBean){
        log.log(Level.INFO,"save guest");
        EditGuestBean guest=new EditGuestBean(guestBean);
        List<EditGuestBean> guests=orderBean.getGuests();
        guests.add(guest);
        guests.stream().forEach(g->log.log(Level.INFO, String.format("guest: %s", g.getName())));
        state="order";
    }

    public void cancelGuest(){
        log.log(Level.INFO,"cancel guest");
        state="order";
    }
}
