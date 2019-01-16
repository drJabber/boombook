package rnk.bb.domain.util;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "country",schema="public")
public class Country {
    @Id
    private Integer id;
    @NotNull
    @Size(max=500)
    @Column(nullable = false)
    private String country;
}
