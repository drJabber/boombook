package rnk.bb.startup.rest;


import rnk.bb.rest.hotel.staff.StaffController;
import rnk.bb.views.HotelHome;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@Startup
public class StartupController {

    @PersistenceContext(unitName="RNK_PU_STARTUP")
    private EntityManager em;

    private static Logger log=Logger.getLogger(StaffController.class.getName());

    @PostConstruct
    void init(){
        log.log(Level.INFO,"Initializing boombook...");
    }
}
