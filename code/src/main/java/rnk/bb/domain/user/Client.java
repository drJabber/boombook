package rnk.bb.domain.user;

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
@Entity
@Table(name="client", schema="public")
public class Client implements Serializable {
    @Id
    @SequenceGenerator(name="client_id_seq",sequenceName = "client_id_seq",schema = "public",allocationSize = 1)
    private Integer id;

    @OneToOne
    @JoinColumn(name="login")
    private Auth login;

    @NotNull
    @Size(max=300)
    private String name;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @NotNull
    @Size(max=2)
    private String gender;

    @OneToOne
    @JoinColumn(name="address_id")
    @NotNull
    private Address address;

    @OneToOne
    @JoinColumn(name="document_id")
    @NotNull
    private Document document;
}
