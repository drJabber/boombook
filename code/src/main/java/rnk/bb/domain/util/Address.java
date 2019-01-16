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
    @SequenceGenerator(name="address_id_seq",sequenceName = "address_id_seq",schema = "public",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="address_id_seq")
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="country_id")
    @NotNull
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
    private String StreetPart;
}
