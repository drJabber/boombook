package rnk.bb.services;

import rnk.bb.domain.auth.Auth;
import rnk.bb.rest.auth.AuthController;
import rnk.bb.rest.user.ClientController;
import rnk.bb.views.EditOrderView;
import rnk.bb.views.bean.registration.RegUserBean;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("registrationService")
@SessionScoped
public class RegistrationService implements Serializable {
    private static Logger log=Logger.getLogger(RegistrationService.class.getName());

    @Inject
    AuthController users;

    @Inject
    ClientController clients;


    public Boolean doRegisterClient(RegUserBean user){
        log.log(Level.INFO, "new client registered");
        users.createUser(user);
//        clients.createClient(user);
        return true;
    }
}
