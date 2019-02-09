package rnk.bb.views;

import rnk.bb.services.HotelService;
import rnk.bb.services.RegistrationService;
import rnk.bb.views.bean.hotel.EditHotelBean;
import rnk.bb.views.bean.registration.StaffUserBean;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("staffRegistrationView")
@SessionScoped
public class StaffRegistrationView implements Serializable {
    private static Logger log=Logger.getLogger(EditOrderView.class.getName());

    private String state;
    private String registrationState;
    private Boolean visible=false;

    StaffUserBean user;


    @Inject
    RegistrationService registrationService;

    @Inject
    HotelService hotelService;

    List<EditHotelBean> hotels;

    @PostConstruct
    public void init(){
        state="reg-user";
        registrationState="notregistered";
        user=new StaffUserBean("hotel-staff");
        this.hotels=new ArrayList<>();
        hotelService.getHotels().stream().forEach(h->this.hotels.add(hotelService.initHotelBean(new EditHotelBean(),h)));
    }


    public void update(){
        init();
        visible=true;
    }

    public void updatemanager(){
        state="reg-user";
        registrationState="notregistered";
        user=new StaffUserBean("hotel-manager");
        this.hotels=new ArrayList<>();
        hotelService.getHotels().stream().forEach(h->this.hotels.add(hotelService.initHotelBean(new EditHotelBean(),h)));

        visible=true;
    }

    public void setStaffAddress(StaffUserBean user){
        log.log(Level.INFO,"add staff address");
        state="staff-address";
    }

    public void saveStaffAddress(StaffUserBean user){
        log.log(Level.INFO,"save  staff address");
        state="reg-user";
    }

    public void cancelStaffAddress(){
        log.log(Level.INFO,"cancel  staff address");
        state="reg-user";
    }


    public void saveStaffDocument(StaffUserBean user){
        log.log(Level.INFO,"save  staff document");
        state="reg-user";
    }

    public void cancelStaffDocument(){
        log.log(Level.INFO,"cancel  staff document");
        state="reg-user";
    }

    public void setStaffDocument(StaffUserBean user){
        log.log(Level.INFO,"add new  staff document");
        state="staff-document";
    }


    public StaffUserBean getUser() {
        return user;
    }

    public void setUser(StaffUserBean user) {
        this.user = user;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String getRegistrationState() {
        return registrationState;
    }

    public void setRegistrationState(String registrationState) {
        this.registrationState = registrationState;
    }

    public List<EditHotelBean> getHotels(){
        return hotels;
    }

    public void doRegisterManager(){
        log.log(Level.INFO,"perform registration...");
        try{
            if (FacesContext.getCurrentInstance().getExternalContext().isUserInRole("hotel-manager"))
            if (registrationService.doRegisterManager(user)){
                registrationState="registered";
            }
        }catch (Exception ex){
            log.log(Level.INFO, String.format("registration failed...\n%s", ex.getMessage()));
            registrationState="failed";
        }

        visible=false;
    }

    public void doCancel(){
        log.log(Level.INFO,"cancel registration");
        registrationState="notregistered";
        visible=false;
    }

    public void closeNotification(){
        registrationState="notregistered";
    }

    public boolean isVisible(){
        return visible;
    }
}
