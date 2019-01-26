package rnk.bb.views;

import org.primefaces.event.SelectEvent;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("editOrderView")
@ViewScoped
public class EditOrderView implements Serializable {
    private static Logger log=Logger.getLogger(EditOrderView.class.getName());

//    private Long orderId=null;
//
//    private Hotel hotel=null;
//    private Order order=null;
//
//    @Inject
//    OrderController orders;
//
//    @Inject
//    HotelService hotelService;

//    @Inject
//    EditOrderBean orderBean;

    @PostConstruct
    public void init(){
//        Map<String, String> params= FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//
//        if (params.get("hotelId")!=null){
//            Long hotelId= Long.valueOf(params.get("hotelId"));
//            hotel=hotelService.findById(hotelId);
//        }
//
//        if (params.get("id")!=null){
//            this.orderId=Long.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
//            order=this.orders.findOptionalById(orderId).orElse(null);
//        }
//            orderBean.init(order);
    }


//    public Hotel getHotel(){
//        return hotel;
//    }

//    public EditOrderBean getOrderBean(){
//        return orderBean;
//    }

    public String getSomeText(){
        return "Добавить гостя!!!";
    }

    public void addDDDDD(){
        log.log(Level.INFO,"ADDD GUEST!!!!!!!!!!!!!");
//        Map<String,Object> options = new HashMap<>();
//        options.put("resizable", false);
//        options.put("draggable", false);
//        options.put("modal", true);
//
//        orderBean=new EditOrderBean();
//
//        PrimeFaces.current().dialog().openDynamic("guestDialog",options,null);
    }

    public void onAddGuest(SelectEvent event){
//        EditGuestBean guest=(EditGuestBean) event.getObject();
//        orderBean.getGuests().add(guest);


    }
}
