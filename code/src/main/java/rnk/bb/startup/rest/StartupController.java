package rnk.bb.startup.rest;


import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@Startup
public class StartupController {

    @PersistenceContext(unitName="RNK_PU_STARTUP")
    private EntityManager em;


    @Inject
    Logger logger;

    @PostConstruct
    void init(){
        logger.log(Level.INFO,"Initializing boombook...");
    }
}
