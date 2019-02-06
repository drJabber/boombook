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
@SessionScoped
public class LoginView implements Serializable {
    private static Logger log=Logger.getLogger(LoginView.class.getName());

    @Inject
    HttpServletRequest request;

    @Inject
    private LoginService loginService;

    @Inject 
    private UserSessionBean userSession;

    private String originalUrl;

    private Auth user=null;

    @PostConstruct
    public void init(){
        ExternalContext    externalContext     = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request             = (HttpServletRequest) externalContext.getRequest();
        String             forwardedRequestURI = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);
        
        if ((this.originalURL = request.getParameter("originalURL")) != null) {
            // If the user was redirected, retrieve the originalURL from the request's "originalURL" parameter
            return;
        
        } else if (forwardedRequestURI == null) { 
            // If the user logged in directly from the top bar, simply redirect to the originalURL recorded by UserSessionBean
            this.originalURL = userSession.getOriginalURL();
            if (originalURL == null) {
                // Redirect to home page in case the user didn't surf any pages before logging in
                this.originalURL = request.getContextPath() + "home.xhtml";
            }
            
        } else {
            // If the user was forwarded to the login page, re-build the orignal requestURL
            this.originalURL     = forwardedRequestURI;
            String originalQuery = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_QUERY_STRING);

            if (originalQuery != null) {
                this.originalURL += "?" + originalQuery;
            }
        }
    }

    public String doLogin(){
        log.log(Level.INFO,"start login...");
        return "/auth/login.xhtml";
    }

    public void logout() throws ServletException {
        log.log(Level.INFO,"logout...");
        request.logout();
        user=null;
    }

    public void login(){
        try { 
            // Some login logic
            //                  ...
            FacesContext.getCurrentInstance().getExternalContext().redirect(originalURL);
        } catch (IOException ex) {
            log.log(Level.INFO,String.format("Login exception:\%s", ex.toString());
        }
}    }

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
