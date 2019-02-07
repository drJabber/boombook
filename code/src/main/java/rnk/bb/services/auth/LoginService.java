package rnk.bb.services.auth;


import rnk.bb.rest.auth.AuthController;
import rnk.bb.views.bean.auth.SessionDataBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named("loginService")
@ApplicationScoped
public class LoginService implements Serializable {

    @Inject
    AuthController users;

    public SessionDataBean findUser(String login){
        return new SessionDataBean(users.getAuth(login));
    }
    
    
}
