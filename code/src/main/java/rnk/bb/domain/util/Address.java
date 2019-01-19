package rnk.bb.domain.util;

import lombok.Data;
import rnk.bb.domain.blank.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name="address", schema = "public")
public class Address extends AbstractEntity {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="country_id")
    private Country country;

    @NotNull
    @Size(max = 20)
    @Column(nullable = false)
    private String zip;

    @NotNull
    @Size(max = 500)
    @Column(nullable = false)
    private String settlementPart;
    @NotNull
    @Size(max = 500)
    @Column(nullable = false)
    private String streetPart;
}
