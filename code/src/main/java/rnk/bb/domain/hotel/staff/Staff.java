package rnk.bb.domain.hotel.staff;

import lombok.Data;
import rnk.bb.domain.auth.Auth;
import rnk.bb.domain.blank.AbstractHotelRefEntity;
import rnk.bb.util.json.DateAdapter;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@Table(name="staff", schema = "public")
public class Staff extends AbstractHotelRefEntity {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="login")
    private Auth login;

    @NotNull
    @Size(max=300)
    @Column(nullable = false)
    private String name;

    @NotNull
    @Temporal(TemporalType.DATE)
    @JsonbTypeAdapter(DateAdapter.class)
    @Column(nullable = false)
    private Date birthDate;

    @NotNull
    @Size(max=2)
    @Column(nullable = false)
    private String gender;
}
