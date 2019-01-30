package rnk.bb.views;

import rnk.bb.domain.book.Order;
import rnk.bb.domain.hotel.resource.Guest;
import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.rest.book.GuestController;
import rnk.bb.services.HotelService;
import rnk.bb.services.OrderService;
import rnk.bb.views.bean.EditGuestBean;
import rnk.bb.views.bean.EditHotelBean;
import rnk.bb.views.bean.EditOrderBean;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("editOrderView")
@SessionScoped
public class EditOrderView implements Serializable {
    private static Logger log=Logger.getLogger(EditOrderView.class.getName());

    private Long orderId=null;
    private Long guestId=null;
    private Long hotelId=null;

    private String state="order";

    @Inject
    GuestController guestController;

    @Inject
    HotelService hotelService;

    @Inject
    OrderService orderService;

    EditHotelBean hotelBean;

    EditOrderBean orderBean;

    EditGuestBean guestBean;

    @PostConstruct
    public void init(){
        Map<String, String> params= FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        if (params.get("hotelId")!=null){
            hotelId= Long.valueOf(params.get("hotelId"));
        }

        if (params.get("orderId")!=null){
            this.orderId=Long.valueOf(params.get("orderId"));
        }

        if (params.get("guestId")!=null){
            this.guestId=Long.valueOf(params.get("guestId"));
        }

        hotelBean=new EditHotelBean();
        hotelService.initHotelBean(hotelBean,hotelId);

        orderBean=new EditOrderBean();
        orderService.initOrderBean(orderBean,orderId);

        guestBean=new EditGuestBean();
        orderService.initGuestBean(guestBean,guestId);

    }


    public EditOrderBean getOrderBean(){
        return orderBean;
    }

    public String getState(){
        return state;
    }


    public EditGuestBean getGuestBean(){
        return guestBean;
    }

    public void addGuest(){
        log.log(Level.INFO,"add new guest");
        this.guestBean=orderService.initGuestBean(guestBean,(EditGuestBean) null);
        state="guest";
    }

    public void saveGuest(EditGuestBean guestBean){
        log.log(Level.INFO,"save guest");
        orderBean.getGuests().add(orderService.initGuestBean(new EditGuestBean(),guestBean));
        state="order";
    }

    public void cancelGuest(){
        log.log(Level.INFO,"cancel guest");
        state="order";
    }

    public void setGuestAddress(EditGuestBean guest){
        log.log(Level.INFO,"add new guest");
        state="guest-address";
    }

    public void saveGuestAddress(EditGuestBean guest){
        log.log(Level.INFO,"save guest address");
//        guest.getAddress().init(guest.getAddress());
        state="guest";
    }

    public void cancelGuestAddress(){
        log.log(Level.INFO,"cancel guest address");
        state="guest";
    }


    public void saveGuestDocument(EditGuestBean guest){
        log.log(Level.INFO,"save guest document");
//        this.guestBean.getDocument().init(guest.getDocument());
        state="guest";
    }

    public void cancelGuestDocument(){
        log.log(Level.INFO,"cancel guest document");
        state="guest";
    }

    public void setGuestDocument(EditGuestBean guest){
        log.log(Level.INFO,"add new document");
//        this.guestBean.getDocument().init(guest.getDocument());
        state="guest-document";
    }

    public EditHotelBean getHotelBean() {
        return hotelBean;
    }

    public void setHotelBean(EditHotelBean hotelBean) {
        this.hotelBean = hotelBean;
    }
}
