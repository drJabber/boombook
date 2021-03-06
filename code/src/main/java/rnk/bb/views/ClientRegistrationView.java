package rnk.bb.views;

import rnk.bb.services.RegistrationService;
import rnk.bb.views.bean.registration.RegUserBean;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("clientRegistrationView")
@SessionScoped
public class ClientRegistrationView implements Serializable {
    private static Logger log=Logger.getLogger(EditOrderView.class.getName());

    private String state;
    private String registrationState;
    private Boolean visible=false;

    RegUserBean user;


    @Inject
    RegistrationService registrationService;

    @PostConstruct
    public void init(){
        state="reg-user";
        registrationState="notregistered";
        user=new RegUserBean();
    }


    public void update(){
        init();
        visible=true;
    }

    public void setClientAddress(RegUserBean user){
        log.log(Level.INFO,"add client address");
        state="client-address";
    }

    public void saveClientAddress(RegUserBean user){
        log.log(Level.INFO,"save  client address");
        state="reg-user";
    }

    public void cancelClientAddress(){
        log.log(Level.INFO,"cancel  client address");
        state="reg-user";
    }


    public void saveClientDocument(RegUserBean user){
        log.log(Level.INFO,"save  client document");
        state="reg-user";
    }

    public void cancelClientDocument(){
        log.log(Level.INFO,"cancel  client document");
        state="reg-user";
    }

    public void setClientDocument(RegUserBean user){
        log.log(Level.INFO,"add new  client document");
        state="client-document";
    }


    public RegUserBean getUser() {
        return user;
    }

    public void setUser(RegUserBean user) {
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

    public void doRegister(){
        log.log(Level.INFO,"perform registration...");
        try{
            if (registrationService.doRegisterClient(user)){
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
