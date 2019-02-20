package rnk.bb.views;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import rnk.bb.domain.hotel.staff.Staff;
import rnk.bb.services.HotelService;
import rnk.bb.services.StaffService;
import rnk.bb.views.bean.hotel.EditHotelBean;
import rnk.bb.views.bean.hotel.EditFoodConceptBean;
import rnk.bb.views.bean.hotel.EditRoomFeatureBean;
import rnk.bb.views.bean.hotel.EditRoomPoolBean;
import rnk.bb.views.bean.registration.StaffUserBean;
import rnk.bb.views.bean.util.HotelImagesView;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.security.Principal;
import java.util.logging.Logger;

@Named("staffView")
@SessionScoped
public class StaffView implements Serializable {
    private static Logger log=Logger.getLogger(StaffView.class.getName());

    private StaffUserBean staffBean;
    private EditFoodConceptBean selectedFoodConcept;
    private EditRoomFeatureBean selectedRoomFeature;
    private EditRoomPoolBean selectedRoomPool;

    private Boolean hotelEditState=false;
    private Boolean editFoodConceptState=false;
    private Boolean editRoomFeatureState=false;
    private Boolean editRoomPoolState=false;
    private String hotelWizardCurrentState="";

    private UploadedFile uploaded;

    @Inject
    HotelService hotelService;

    @Inject
    StaffService staffService;

    @Inject
    HotelImagesView imagesView;


    @PostConstruct
    public void init(){
        initInternal(false);
    }

    private void initInternal(Boolean reset){
        ExternalContext exContext=FacesContext.getCurrentInstance().getExternalContext();

        Principal principal=exContext.getUserPrincipal();
        if (staffBean==null){
            staffBean=new StaffUserBean();
        }
        if (principal!=null){
            String userName=principal.getName();
            Staff staff=staffService.findByLogin(userName);
            staffService.initStaffBean(staffBean,staff,reset);
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

    public void resetHotelBean(){
        initInternal(true);
        PrimeFaces.current().executeScript("PF('hotelWizardW').loadStep('hotelInfoTab', false)");
    }

    public String saveHotel(){
        hotelEditState=false;
        staffService.doSaveStaff(staffBean,true);
        initInternal(false);
        PrimeFaces.current().executeScript("PF('hotelWizardW').loadStep('hotelInfoTab', false)");
        return "#";
    }

    public void publishHotel(){
        if (hotelApproved()){
            staffService.publishHotel(staffBean.getApproval().getAwaitingHotel(), staffBean);
        }
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
            if (staffBean.hasAwaitingHotel()){
                return "hotelConfirmTab";
            }else{
                return "hotelEditGeneralTab";
            }
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
        if (hotelCanRequestApproval()){
            hotelService.requestApproval(staffBean.getApproval());
        }
        return "";
    }

    public Boolean hotelCanRequestApproval(){
        return (!hotelEditState)
                &&(staffBean.getApproval()!=null)
                &&(staffBean.getApproval().getAwaitingHotel()!=null)
                &&(staffBean.getApproval().getAwaitingHotel().getId()!=null)
                &&(staffBean.getApproval().getApprovedState()==0);
    }

    public boolean hotelApproved(){
        return staffBean.getApprovedState();
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


    public EditRoomPoolBean getSelectedRoomPool(){
        return selectedRoomPool;
    }

    public void setSelectedRoomPool(EditRoomPoolBean fc){
        this.selectedRoomPool=fc;
    }

    public Boolean getEditRoomPoolState(){
        return this.editRoomPoolState;
    }

    public void setEditRoomPoolState(Boolean state){
        this.editRoomPoolState=state;
    }

    public void editRoomPool(){
        if (selectedRoomPool==null){
            selectedRoomPool=new EditRoomPoolBean();
        }
        this.editRoomPoolState=true;
    }

    public void removeRoomPool(){
        hotelService.removeRoomPoolBean(staffBean.getHotel(),selectedRoomPool);
        this.selectedRoomPool=null;
    }

    public void cancelRoomPool(){
        this.editRoomPoolState=false;
        this.selectedRoomPool=null;
    }

    public void saveRoomPool(){
        hotelService.saveRoomPoolBean(staffBean.getHotel(),selectedRoomPool);
        this.editRoomPoolState=false;
        this.selectedRoomPool=null;
    }

    public UploadedFile getUploaded(){
        return uploaded;
    }

    public void setUploaded(UploadedFile uploaded){
        this.uploaded=uploaded;
    }

    public StreamedContent getUploadedHotelImage(){
        return  imagesView.getStreamedContent(staffBean.getHotel().getPicture());
    }


    public void handleHotelPictureUpload(FileUploadEvent event){
        staffBean.getHotel().setPicture(event.getFile().getContents());
    }
}
