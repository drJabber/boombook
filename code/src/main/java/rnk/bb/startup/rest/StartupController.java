package rnk.bb.startup.rest;


import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Path;

@Singleton
@Startup
public class StartupController {

    @PersistenceContext(unitName="RNK_PU_STARTUP")
    private EntityManager em;

}
