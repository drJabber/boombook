package rnk.bb.services;

import rnk.bb.rest.hotel.staff.StaffController;
import rnk.bb.rest.user.ClientController;
import rnk.bb.views.bean.registration.RegUserBean;
import rnk.bb.views.bean.registration.StaffUserBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("registrationService")
@ApplicationScoped
public class RegistrationService implements Serializable {
    private static Logger log=Logger.getLogger(RegistrationService.class.getName());

    @Inject
    ClientController clients;

    @Inject
    StaffController staff_list;

    public Boolean doRegisterClient(RegUserBean user){
        log.log(Level.INFO, "new client registered");
        clients.registerClient(user);
        return true;
    }

    public Boolean doRegisterStaff(StaffUserBean user){
        log.log(Level.INFO, "new hotel staff registered");
        staff_list.registerStaff(user);
        return true;
    }

    public Boolean doRegisterManager(StaffUserBean user){
        log.log(Level.INFO, "new hotel staff registered");
        staff_list.registerManager(user);
        return true;
    }
}
