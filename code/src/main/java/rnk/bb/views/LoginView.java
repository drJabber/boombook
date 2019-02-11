package rnk.bb.views;

import rnk.bb.services.auth.LoginService;
import rnk.bb.views.bean.auth.SessionDataBean;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("loginView")
@ViewScoped
public class LoginView implements Serializable {
    private static Logger log=Logger.getLogger(LoginView.class.getName());

    private Boolean visible=false;
    private String notification="closed";

    @Inject
    HttpServletRequest request;

    @Inject
    private LoginService loginService;

    @Inject 
    private SessionDataBean userSession;

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
            // If the user logged in directly from the top bar, simply redirect to the originalURL recorded by SessionDataBean
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

    public void logout() throws ServletException, IOException {
        log.log(Level.INFO,"logout...");
        userSession=new SessionDataBean();
        request.logout();
        request.getSession().invalidate();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath());
    }


    public void login(){
        log.log(Level.INFO,String.format(String.format("perform login. password : %s", userSession.getPassword())));
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        String login=userSession.getLogin();
        try {
            request.login(login, userSession.getPassword());

            userSession.setLoggedIn(true);
            notification="open";


            if (request.isUserInRole("boom-manager")){
                userSession.setNotificationMessage(String.format("System manager %s logged in", login));
                externalContext.redirect(request.getContextPath()+ "/admin/admin.xhtml");
            }else if (request.isUserInRole("hotel-manager")){
                userSession.setNotificationMessage(String.format("Hotel manager %s logged in", login));
                externalContext.redirect(request.getContextPath()+"/hotel/hotelstaff.xhtml");
            }else{

                userSession.setNotificationMessage(String.format("Client %s logged in", login));
                externalContext.redirect(originalUrl);
            }
        } catch (Exception ex) {
            notification="open";
            userSession.setNotificationMessage(String.format("LOGIN FAILED FOR %s", login));
            log.log(Level.SEVERE,String.format("Login exception:\n%s", ex.toString()));
        }
    }

    public void cancel(){
        log.log(Level.INFO,"cancel login");
        visible=false;
    }

    public SessionDataBean getUser() {
        if (userSession.getLogin().isEmpty()) {
            Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
            if (principal != null) {
                userSession = loginService.findUser(principal.getName());
            }
        }
        return userSession;
    }

    public void setUser(SessionDataBean user) {
        this.userSession = user;
    }

    public Boolean isVisible() {
        return visible;
    }

    public String getNotificationState() {
        return notification;
    }

    public void closeNotification(){
        notification="closed";
    }

    public String getNotificationMessage() {
        return userSession.getNotificationMessage();
    }
}

