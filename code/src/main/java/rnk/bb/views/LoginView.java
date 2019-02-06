package rnk.bb.views;

import rnk.bb.domain.auth.Auth;
import rnk.bb.services.auth.LoginService;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.security.Principal;

@Named("loginView")
@ViewScoped
public class LoginView implements Serializable {

    @Inject
    HttpServletRequest request;

    @Inject
    private LoginService loginService;

    private Auth user=null;

    @PostConstruct
    public void init(){

    }

    public void logout() throws ServletException {
        request.logout();
        user=null;
    }

    public Auth getUser() {
        if (user == null) {
            Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
            if (principal != null) {
                user = loginService.findUser(principal.getName());
            }
        }
        return user;
    }

    public void setUser(Auth user) {
        this.user = user;
    }
}
