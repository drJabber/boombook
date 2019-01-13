package rnk.bb.auth.rest.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfo implements Serializable {
    private String login;
    private String password;
    private String redirectTo;
}
