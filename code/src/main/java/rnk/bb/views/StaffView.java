package rnk.bb.views;

import org.primefaces.PrimeFaces;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.domain.hotel.staff.Staff;
import rnk.bb.rest.hotel.resource.HotelController;
import rnk.bb.services.HotelService;
import rnk.bb.services.StaffService;
import rnk.bb.views.bean.hotel.EditHotelBean;
import rnk.bb.views.bean.registration.StaffUserBean;
import rnk.bb.views.util.WizardButtonTitles;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Named("staffView")
@SessionScoped
public class StaffView implements Serializable {
    private static Logger log=Logger.getLogger(StaffView.class.getName());

    private StaffUserBean staffBean;

    private Boolean hotelEditState=false;
    private String hotelWizardCurrentState="";

    @Inject
    HotelService hotelService;

    @Inject
    StaffService staffService;

    @PostConstruct
    public void init(){
        ExternalContext exContext=FacesContext.getCurrentInstance().getExternalContext();

        Principal principal=exContext.getUserPrincipal();
        if (staffBean==null){
            staffBean=new StaffUserBean();
        }
        if (principal!=null){
            String userName=principal.getName();
            Staff staff=staffService.findByLogin(userName);
            staffService.initStaffBean(staffBean,staff);
        }
    }

    private Boolean isNewHotel(){
        return staffBean.getHotel().getId()==null;
    }

    public void update(){
        init();
    }

    public String getEditHotelButtonTitle(){
        if (isNewHotel()){
            return "Зарегистрировать новый отель";
        }else{
            return "Изменить данные отеля";
        }
    }

    public String editHotel(){
        hotelEditState=true;

        return "";
    }

    public String saveHotel(){
        hotelEditState=false;
        PrimeFaces.current().executeScript("PF('hotelWizardW').loadStep('hotelInfoTab', false)");
//        PrimeFaces.current().ajax().update("tabs");
        return "#";
    }

    public String onHotelFlow(FlowEvent event){
        String step=event.getNewStep();
        return step;
    }


    public Boolean getShowHotelWizardNavBar(){
        return hotelEditState;
    }

    public void setHotelEditState(Boolean editState){
        this.hotelEditState=editState;
    }

    public Boolean getHotelEditState(){
        return this.hotelEditState;
    }

    public String getHotelWizardCurrentStep(){
        if (hotelEditState){
            return "hotelEditGeneralTab";
        }else{
            return "hotelInfoTab";
        }
    }

    public String getEditHotelHeaderTitle(){
        if (staffBean.getHotel().getId()==null){
            return "Регистрация отеля";
        }else{
            return "Изменение данных отеля";
        }
    }
    public String requestApproval(){
        return "";
    }


    public StaffUserBean getUser(){
        return staffBean;
    }

    public EditHotelBean getHotel(){return staffBean.getHotel();}
}
