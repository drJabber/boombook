package rnk.bb.services.auth;


import rnk.bb.domain.auth.Auth;
import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.domain.hotel.staff.Staff;
import rnk.bb.rest.auth.AuthController;
import rnk.bb.views.bean.auth.SessionDataBean;
import rnk.bb.views.bean.registration.StaffUserBean;

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


    public SessionDataBean initAccountBean(SessionDataBean accountBean, String accountId){
        Auth auth=null;
        if (accountId!=null){
            auth=this.users.findOptionalById(accountId).orElse(null);
        }
        SessionDataBean result=accountBean;
        if (accountBean==null)
        {
            result=new SessionDataBean();
        }
        return initAccountBean(result,auth);
    }


    private SessionDataBean cleanAccountBean(SessionDataBean accountBean){
        accountBean.setLogin("");
        accountBean.setPassword("");

        return accountBean;
    }

    public SessionDataBean initAccountBean(SessionDataBean accountBean, Auth auth){
        if (auth!=null) {
            accountBean.setLogin(auth.getLogin());

            return accountBean;
        }else{
            return cleanAccountBean(accountBean);
        }
    }

    public SessionDataBean initAccountBean(SessionDataBean accountBean, SessionDataBean anotherBean){
        if ((accountBean!=null)&&(anotherBean!=null)) {
            Auth auth=this.users.findOptionalById(anotherBean.getLogin()).orElse(null);

            accountBean.setLogin(auth.getLogin());

            return accountBean;
        }else{
            return cleanAccountBean(accountBean);
        }
    }

}
