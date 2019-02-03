package rnk.bb.views;

import rnk.bb.views.bean.order.EditGuestBean;
import rnk.bb.views.bean.registration.RegUserBean;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("registrationView")
@SessionScoped
public class RegistrationView implements Serializable {
    private static Logger log=Logger.getLogger(EditOrderView.class.getName());

    private String state="reg-user";

    RegUserBean user=new RegUserBean();


    public void setClientAddress(RegUserBean user){
        log.log(Level.INFO,"add client address");
        state="guest-address";
    }

    public void saveClientAddress(RegUserBean user){
        log.log(Level.INFO,"save  client address");
        state="guest";
    }

    public void cancelClientAddress(){
        log.log(Level.INFO,"cancel  client address");
        state="guest";
    }


    public void saveClientDocument(RegUserBean user){
        log.log(Level.INFO,"save  client document");
        state="guest";
    }

    public void cancelClientDocument(){
        log.log(Level.INFO,"cancel  client document");
        state="guest";
    }

    public void setClientDocument(RegUserBean user){
        log.log(Level.INFO,"add new  client document");
        state="guest-document";
    }


    public RegUserBean getUser() {
        return user;
    }

    public void setUser(RegUserBean user) {
        this.user = user;
    }


    public void doRegister(){
        log.log(Level.INFO,"do registration...");

    }
    public String cancel(){
        log.log(Level.INFO,"cancel registration");
        return "    window.history.back();";
    }
}
