package rnk.bb.services.bean;


import lombok.Data;
import lombok.NoArgsConstructor;
import rnk.bb.domain.util.Country;


@Data
@NoArgsConstructor
public class CountryBean {
    private Integer id;
    private String name;

    public CountryBean(Country country){
        this.id=country.getId();
        this.name=country.getNameRu();
    }
}
