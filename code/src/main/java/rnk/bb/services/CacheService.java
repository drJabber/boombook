package rnk.bb.services;


import rnk.bb.domain.util.Country;
import rnk.bb.domain.util.DocumentType;
import rnk.bb.rest.util.CountryController;
import rnk.bb.rest.util.DocumentTypeController;
import rnk.bb.services.bean.CountryBean;
import rnk.bb.services.bean.DocumentTypeBean;

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
    @Inject
    CountryController countriesController;

    @Inject
    DocumentTypeController documentTypeController;

    private List<CountryBean> countries;
    private List<DocumentTypeBean> documentTypes;

    @PostConstruct
    public void init(){
        this.countries=new ArrayList<>();
        List<Country> countries=countriesController.findAll();
        this.countries.clear();
        countries.stream().forEach(c->this.countries.add(new CountryBean(c)));

        this.documentTypes=new ArrayList<>();
        List<DocumentType> list=documentTypeController.findAll();
        this.documentTypes.clear();
        list.stream().forEach(t->this.documentTypes.add(new DocumentTypeBean(t.getDescription())));
    }


    public List<CountryBean> getCountries(){
        return countries;
    }

    public CountryBean getCountryById(Integer countryId){
        return countries.stream().filter(c->c.getId()==countryId).findFirst().get();
    }

    public List<DocumentTypeBean> getDocumentTypes(){
        return documentTypes;
    }

    public DocumentTypeBean getDocumentById(String id){
        return documentTypes.stream().filter(c->c.getDescription()==id).findFirst().get();
    }

}
