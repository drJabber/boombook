package rnk.bb.startup.rest;


import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;

@Singleton
@Startup
@Path("/v1")
public class StartupController {

    @PersistenceContext(unitName="RNK_PU_STARTUP")
    private EntityManager em;

}
