package rnk.bb.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="role", schema="public")
@Data
@NoArgsConstructor
public class Role implements Serializable {
    @NotNull
    @Size(max=100)
    @Id
    private String role;

    @JoinColumn(name="login")
    @ManyToMany(cascade=CascadeType.ALL)
    private Set<Auth> accounts=new HashSet<>();

    public Set<Auth> getAccounts(){
        return this.accounts;
    }

    public void setAccounts(Set<Auth> accounts){
        this.accounts=accounts;
    }

    public Role(String role){
        this.role=role;
    }
}
