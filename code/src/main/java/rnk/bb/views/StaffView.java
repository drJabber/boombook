package rnk.bb.views;

import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.domain.hotel.staff.Staff;
import rnk.bb.rest.hotel.resource.HotelController;
import rnk.bb.services.HotelService;
import rnk.bb.services.StaffService;
import rnk.bb.views.bean.registration.StaffUserBean;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.security.Principal;
import java.util.logging.Logger;

@Named("staffView")
@ViewScoped
public class StaffView implements Serializable {
    private static Logger log=Logger.getLogger(StaffView.class.getName());

    private StaffUserBean staffBean;

    @Inject
    HotelService hotelService;

    @Inject
    StaffService staffService;

    @PostConstruct
    public void init(){
        ExternalContext exContext=FacesContext.getCurrentInstance().getExternalContext();

        Long hotelId= Long.valueOf(exContext.getRequestParameterMap().get("id"));
//        hotel=hotelService.findById(hotelId);

        Principal principal=exContext.getUserPrincipal();
        if (principal!=null){
            String userName=principal.getName();
            Staff staff=staffService.findByLogin(userName);
            staffService.initStaffBean(staffBean,staff);
        }
    }

    public void update(){
        init();
    }

//    public Hotel getHotel(){
//        return hotel;
//    }

    public String getEditHotelButtonTitle(){
        return "Создать отель";
    }

    public StaffUserBean getUser(){
        return staffBean;
    }
}
