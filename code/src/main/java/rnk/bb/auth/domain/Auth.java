package rnk.bb.auth.domain;

import lombok.Data;
import rnk.utils.security.HashUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name="auth", schema="public")
public class Auth implements Serializable {
    @Size(max=100)
    @NotNull
    @Id
    private String login;

    @NotNull
    @Size(max=200)
    private String password;

    @NotNull
    @Size(max=100)
    private String email;

    @NotNull
    @Size(max=100)
    private String phone;

    @NotNull
    private Boolean blocked=false;

    @JoinColumn(name="role")
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "accounts")
    private Set<Role> roles=new HashSet<>();

    public String getLogin(){
        return this.login;
    }

    public void setLogin(String login){
        this.login=login;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        HashUtils hu=new HashUtils();
        byte[] bsalt=hu.salt(24);
        String salt=hu.toBase64(bsalt);
        byte[] bpaswd=hu.hash_strong(password,bsalt);
        this.password=salt+"#"+hu.toBase64(bpaswd);
    }

    public Set<Role> getRoles(){
        return this.roles;
    }

    public void setRoles(Set<Role> roles){
        this.roles=roles;
    }


}
