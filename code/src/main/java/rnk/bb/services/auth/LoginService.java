package rnk.bb.services.auth;


import rnk.bb.domain.auth.Auth;
import rnk.bb.rest.auth.AuthController;
import rnk.bb.views.bean.auth.AuthUserBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named("loginService")
@ApplicationScoped
public class LoginService implements Serializable {

    @Inject
    AuthController users;

    public AuthUserBean findUser(String login){
        return new AuthUserBean(users.getAuth(login));
    }
    
    
}
