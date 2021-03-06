package rnk.bb.domain.util;

import lombok.Data;
import lombok.EqualsAndHashCode;
import rnk.bb.domain.blank.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@EqualsAndHashCode(callSuper = false)
@Data
@Table(name="address", schema = "public")
public class Address extends AbstractEntity {
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name="country_id")
    private Country country;

    @Size(max = 20)
    @Column(nullable = true)
    private String zip;

    @Size(max = 500)
    @Column(nullable = true)
    private String settlementPart;

    @Size(max = 500)
    @Column(nullable = true)
    private String streetPart;

    @NotNull
    @Column(nullable = false)
    private Integer externalId=0;


    public int hashCode(){
        return getId().hashCode();
    }

}
