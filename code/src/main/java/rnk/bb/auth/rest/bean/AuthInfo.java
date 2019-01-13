package rnk.bb.auth.rest.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rnk.bb.auth.domain.Auth;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthInfo {
    private String login;
    private String password;
    private String email;
    private String phone;
    private Boolean blocked;
    private List<String> roles;


    public AuthInfo(Auth auth){
        this.login=auth.getLogin();
        this.password=auth.getPassword();
        this.email=auth.getEmail();
        this.phone=auth.getPhone();
        this.blocked=auth.getBlocked();
        this.roles=createRoles(auth);
    }

    private List<String> createRoles(Auth auth){
        List<String> roles=new ArrayList<>();
        auth.getRoles().stream().forEach(r->roles.add(r.getRole()));
        return roles;
    }
}
