package rnk.bb.domain.util;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name="address", schema = "public")
public class Address {
    @Id
    private Integer id;

    @OneToOne @JoinColumn(name="country_id")
    @NotNull
    private Country country;

    @NotNull
    @Size(max = 20)
    private String zip;

    @NotNull
    @Size(max = 500)
    private String settlementPart;
    @NotNull
    @Size(max = 500)
    private String StreetPart;
}