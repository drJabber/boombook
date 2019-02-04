package rnk.bb.services;

import rnk.bb.views.EditOrderView;
import rnk.bb.views.bean.registration.RegUserBean;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("registrationService")
@SessionScoped
public class RegistrationService implements Serializable {
    private static Logger log=Logger.getLogger(RegistrationService.class.getName());
    public Boolean doRegister(RegUserBean user){
        log.log(Level.INFO, "new client registered");
        return true;
    }
}
