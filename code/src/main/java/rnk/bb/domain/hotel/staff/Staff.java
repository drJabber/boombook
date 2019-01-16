package rnk.bb.domain.hotel.staff;

import lombok.Data;
import rnk.bb.domain.auth.Auth;
import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.domain.util.Address;
import rnk.bb.helper.json.DateAdapter;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@Table(name="staff", schema = "public")
public class Staff {
    @Id
    @SequenceGenerator(name="staff_id_seq",sequenceName = "staff_id_seq",schema = "public",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="staff_id_seq")
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="hotel_id")
    private Hotel hotel;


}
