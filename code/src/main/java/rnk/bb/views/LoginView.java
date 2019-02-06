package rnk.bb.views;

import rnk.bb.domain.auth.Auth;
import rnk.bb.services.auth.LoginService;
import rnk.bb.views.bean.auth.AuthUserBean;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("loginView")
@ViewScoped
public class LoginView implements Serializable {
    private static Logger log=Logger.getLogger(LoginView.class.getName());

    private Boolean visible=false;

    @Inject
    HttpServletRequest request;

    @Inject
    private LoginService loginService;

    @Inject 
    private AuthUserBean userSession;

    private String originalUrl;

    @PostConstruct
    public void init(){
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        String forwardedRequestURI = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);
        
        if ((this.originalUrl = request.getParameter("originalURL")) != null) {
            // If the user was redirected, retrieve the originalURL from the request's "originalURL" parameter
            return;
        
        } else if (forwardedRequestURI == null) { 
            // If the user logged in directly from the top bar, simply redirect to the originalURL recorded by AuthUserBean
            this.originalUrl = userSession.getOriginalURL();
            if (originalUrl == null) {
                // Redirect to home page in case the user didn't surf any pages before logging in
                this.originalUrl = request.getContextPath() + "home.xhtml";
            }
            
        } else {
            // If the user was forwarded to the login page, re-build the orignal requestURL
            this.originalUrl = forwardedRequestURI;
            String originalQuery = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_QUERY_STRING);

            if (originalQuery != null) {
                this.originalUrl += "?" + originalQuery;
            }
        }
    }

    public void update(){
        visible=true;
    }

    public String doLogin(){
        log.log(Level.INFO,"start login...");
        return "/auth/login.xhtml";
    }

    public void logout() throws ServletException {
        log.log(Level.INFO,"logout...");
        request.logout();
        userSession=null;
    }


    public void login(){
        log.log(Level.INFO,String.format(String.format("perform login. password : %s", userSession.getPassword())));
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        try {
            request.login(userSession.getLogin(), userSession.getPassword());

            userSession.setLoggedIn(true);
            externalContext.redirect(originalUrl);
        } catch (Exception ex) {
            log.log(Level.SEVERE,String.format("Login exception:\n%s", ex.toString()));
        }
    }

    public void cancel(){
        log.log(Level.INFO,"cancel login");
        visible=false;
    }

    public AuthUserBean getUser() {
        if (userSession.getLogin() == null) {
            Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
            if (principal != null) {
                userSession = loginService.findUser(principal.getName());
            }
        }
        return userSession;
    }

    public void setUser(AuthUserBean user) {
        this.userSession = user;
    }

    public Boolean isVisible() {
        return visible;
    }
}

