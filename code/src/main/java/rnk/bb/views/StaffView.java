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
import rnk.bb.views.bean.order.EditFoodConceptBean;
import rnk.bb.views.bean.order.EditRoomFeatureBean;
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
    private EditFoodConceptBean selectedFoodConcept;
    private EditRoomFeatureBean selectedRoomFeature;

    private Boolean hotelEditState=false;
    private Boolean editFoodConceptState=false;
    private Boolean editRoomFeatureState=false;
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

    public StaffUserBean getUser(){
        return staffBean;
    }

    public EditHotelBean getHotel(){return staffBean.getHotel();}

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


    public EditFoodConceptBean getSelectedFoodConcept(){
        return selectedFoodConcept;
    }

    public void setSelectedFoodConcept(EditFoodConceptBean fc){
        this.selectedFoodConcept=fc;
    }

    public Boolean getEditFoodConceptState(){
        return this.editFoodConceptState;
    }

    public void setEditFoodConceptState(Boolean state){
        this.editFoodConceptState=state;
    }

    public void editFoodConcept(){
        if (selectedFoodConcept==null){
            selectedFoodConcept=new EditFoodConceptBean();
        }
        this.editFoodConceptState=true;
    }

    public void removeFoodConcept(){
        hotelService.removeFoodConceptBean(staffBean.getHotel(),selectedFoodConcept);
        this.selectedFoodConcept=null;
    }

    public void cancelFoodConcept(){
        this.editFoodConceptState=false;
        this.selectedFoodConcept=null;
    }

    public void saveFoodConcept(){
        hotelService.saveFoodConceptBean(staffBean.getHotel(),selectedFoodConcept);
        this.editFoodConceptState=false;
        this.selectedFoodConcept=null;
    }

    public EditRoomFeatureBean getSelectedRoomFeature(){
        return selectedRoomFeature;
    }

    public void setSelectedRoomFeature(EditRoomFeatureBean fc){
        this.selectedRoomFeature=fc;
    }

    public Boolean getEditRoomFeatureState(){
        return this.editRoomFeatureState;
    }

    public void setEditRoomFeatureState(Boolean state){
        this.editRoomFeatureState=state;
    }

    public void editRoomFeature(){
        if (selectedRoomFeature==null){
            selectedRoomFeature=new EditRoomFeatureBean();
        }
        this.editRoomFeatureState=true;
    }

    public void removeRoomFeature(){
        hotelService.removeRoomFeatureBean(staffBean.getHotel(),selectedRoomFeature);
        this.selectedRoomFeature=null;
    }

    public void cancelRoomFeature(){
        this.editRoomFeatureState=false;
        this.selectedRoomFeature=null;
    }

    public void saveRoomFeature(){
        hotelService.saveRoomFeatureBean(staffBean.getHotel(),selectedRoomFeature);
        this.editRoomFeatureState=false;
        this.selectedRoomFeature=null;
    }

}
