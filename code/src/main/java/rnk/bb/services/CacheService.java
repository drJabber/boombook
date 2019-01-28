package rnk.bb.services;


import rnk.bb.domain.util.Country;
import rnk.bb.rest.util.CountryController;
import rnk.bb.services.bean.CountryBean;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named("cacheService")
@ApplicationScoped
public class CacheService {
    @Inject @Dependent
    CountryController countriesController;

    private List<CountryBean> countries;

    @PostConstruct
    public void init(){
        this.countries=new ArrayList<>();
        List<Country> countries=countriesController.findAll();
        this.countries.clear();
        countries.stream().forEach(c->this.countries.add(new CountryBean(c)));
    }


    public List<CountryBean> getCountries(){
        return countries;
    }

}
