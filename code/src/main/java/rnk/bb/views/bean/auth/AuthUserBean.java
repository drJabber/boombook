package rnk.bb.views.bean.auth;

import rnk.bb.domain.auth.Auth;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Named("userSession")
@SessionScoped
public class AuthUserBean implements Serializable {
    private boolean loggedIn;
    private String  originalURL;

    @NotNull
    private String login="";

    @NotNull
    private String password="";

    public AuthUserBean(){

    }

    public AuthUserBean(Auth auth){
        this.setLogin(auth.getLogin());
//        this.setPassword(auth.getPassword());
    }

    public void recordOriginalURL(String originalURL) {
        this.originalURL = originalURL;
    }

    public String getOriginalURL(){
        return originalURL;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}