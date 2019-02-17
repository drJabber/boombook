package rnk.bb.domain.util;

import lombok.Data;
import rnk.bb.domain.blank.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "country",schema="public")
public class Country implements Serializable {
    @Id
    private Integer id;

    @NotNull
    @Size(max=500)
    @Column(name = "name_ru", nullable = false)
    private String nameRu;

    @NotNull
    @Size(max=500)
    @Column(name = "name_en", nullable = false)
    private String nameEn;

    @NotNull
    @Size(max=2)
    @Column(name = "iso2", nullable = false)
    private String iso2;

    @NotNull
    @Size(max=3)
    @Column(name = "iso3", nullable = false)
    private String iso3;

    @Column(name = "order_id", nullable = true)
    private Integer order;


    @OneToMany(mappedBy = "country")
    private List<Address> addresses;

    public int hashCode(){
        return nameRu.hashCode();
    }

}
