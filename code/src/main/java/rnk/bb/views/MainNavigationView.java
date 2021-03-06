package rnk.bb.views;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("navView")
@ApplicationScoped
public class MainNavigationView implements Serializable {
    private static Logger log=Logger.getLogger(MainNavigationView.class.getName());

    public String getClientRegUrl(){
        return "/register/register.xhtml";
    }

    public String getHManagerRegUrl(){
        return "/register/hregister.xhtml";
    }

    public String getLoginUrl(){
        return "/auth/login.xhtml";
    }

}
