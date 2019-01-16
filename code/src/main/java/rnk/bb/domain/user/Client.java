package rnk.bb.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import rnk.bb.domain.auth.Auth;
import rnk.bb.domain.util.Address;
import rnk.bb.domain.util.Document;
import rnk.bb.helper.json.DateAdapter;

import javax.json.bind.annotation.JsonbTypeAdapter;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="client_id_seq")
//    @JsonIgnore
    private Integer id;

    @OneToOne
    @JoinColumn(name="login")
    private Auth login;

    @NotNull
    @Size(max=300)
    private String name;

    @NotNull
    @Temporal(TemporalType.DATE)
    @JsonbTypeAdapter(DateAdapter.class)
    private Date birthDate;

    @NotNull
    @Size(max=2)
    private String gender;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="address_id")
    private Address address;

    @OneToOne
    @JoinColumn(name="document_id")
    private Document document;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

}
