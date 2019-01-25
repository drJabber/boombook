package rnk.bb.views;

import rnk.bb.domain.book.Order;
import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.rest.book.OrderController;
import rnk.bb.services.HotelNotFoundException;
import rnk.bb.services.HotelService;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;

@Named("editOrderView")
@ViewScoped()
public class EditOrderView implements Serializable {
    private Long orderId=null;

    private Hotel hotel;
    private Order order=null;

    @Inject
    OrderController orders;

    @Inject
    HotelService hotelService;

    @Inject
    EditOrderBean orderBean;

    @PostConstruct
    public void init(){
        Map<String, String> params= FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Long hotelId= Long.valueOf(params.get("hotelId"));
        hotel=hotelService.findById(hotelId);

        if (params.get("id")!=null){
            this.orderId=Long.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
            order=this.orders.findOptionalById(orderId).orElse(null);
        }
        orderBean.init(order);
    }


    public Hotel getHotel(){
        return hotel;
    }

    public EditOrderBean getOrderBean(){
        return orderBean;
    }

    public  void validate(ComponentSystemEvent event){

    }
}
