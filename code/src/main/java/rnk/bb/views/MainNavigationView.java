package rnk.bb.views;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("navView")
@ApplicationScoped
public class MainNavigationView implements Serializable {
    private static Logger log=Logger.getLogger(EditOrderView.class.getName());

    public String doRegister(){
        log.log(Level.INFO,"start registration...");
        return "/register/register.xhtml";
    }
    
}
