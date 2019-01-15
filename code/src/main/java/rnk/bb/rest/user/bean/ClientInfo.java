package rnk.bb.rest.user.bean;

import lombok.Data;
import rnk.bb.domain.auth.Auth;
import rnk.bb.domain.util.Address;
import rnk.bb.domain.util.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
public class ClientInfo implements Serializable {
    @NotNull
    private String login;

    @NotNull
    @Size(max=300)
    private String name;

    @NotNull
    private String birthDate;

    @NotNull
    @Size(max=2)
    private String gender;
}
