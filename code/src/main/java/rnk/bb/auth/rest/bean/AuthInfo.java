package rnk.bb.auth.rest.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import rnk.bb.auth.domain.Auth;

import javax.validation.constraints.NotNull;
import javax.ws.rs.QueryParam;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@XmlRootElement
public class AuthInfo implements Serializable {
    @QueryParam(value = "login")
    private String login;
    @QueryParam(value = "password")
    private String password;
    @QueryParam(value = "email")
    private String email;
    @QueryParam(value = "phone")
    private String phone;
//    @NotNull
//    @QueryParam("blocked")
//    private Boolean blocked;

//    @NotNull
//    @QueryParam("roles")
//    private List<String> roles;


    public AuthInfo(){
//        this.roles=new ArrayList<>();

    }

    public AuthInfo(String login, String password, String email, String phone){
        this.login=login;
        this.password=password;
        this.phone=phone;
        this.email=email;
//        this.blocked=blocked;
//        this.roles=new ArrayList<>(roles);
    }

    public AuthInfo(Auth auth){
        this.login=auth.getLogin();
        this.password=auth.getPassword();
        this.email=auth.getEmail();
        this.phone=auth.getPhone();
//        this.blocked=auth.getBlocked();
//        this.roles=createRoles(auth);
    }

    private List<String> createRoles(Auth auth){
        List<String> roles=new ArrayList<>();
        auth.getRoles().stream().forEach(r->roles.add(r.getRole()));
        return roles;
    }
}
