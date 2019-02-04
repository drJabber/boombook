package rnk.bb.services;


import rnk.bb.domain.auth.Role;
import rnk.bb.domain.hotel.resource.Room;
import rnk.bb.domain.hotel.resource.RoomType;
import rnk.bb.domain.util.Country;
import rnk.bb.domain.util.DocumentType;
import rnk.bb.rest.auth.AuthController;
import rnk.bb.rest.hotel.resource.RoomTypeController;
import rnk.bb.rest.util.CountryController;
import rnk.bb.rest.util.DocumentTypeController;
import rnk.bb.services.bean.CountryBean;
import rnk.bb.services.bean.DocumentTypeBean;
import rnk.bb.services.bean.RoleBean;
import rnk.bb.services.bean.RoomTypeBean;

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

    @Inject
    RoomTypeController roomTypeController;

    @Inject
    AuthController authController;

    private List<CountryBean> countries;
    private List<DocumentTypeBean> documentTypes;
    private List<RoomTypeBean> roomTypes;
    private List<RoleBean> roles;

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

        this.roomTypes=new ArrayList<>();
        List<RoomType> typesList=roomTypeController.findAll();
        this.roomTypes.clear();
        typesList.stream().forEach(t->this.roomTypes.add(new RoomTypeBean(t)));

        this.roles=new ArrayList<>();
        List<Role> rolesList=authController.getRoles();
        rolesList.stream().forEach(r->this.roles.add(new RoleBean(r.getRole())));
    }


    public List<CountryBean> getCountries(){
        return countries;
    }

    public CountryBean getCountryById(Integer countryId){
        return countries.stream().filter(c->c.getId().equals(countryId)).findFirst().orElse(null);
    }

    public List<DocumentTypeBean> getDocumentTypes(){
        return documentTypes;
    }

    public DocumentTypeBean getDocumentById(String id){
        return documentTypes.stream().filter(c->c.getDescription()==id).findFirst().get();
    }

    public List<RoomTypeBean> getRoomTypes(){
        return roomTypes;
    }

    public RoomTypeBean getRoomTypeById(String id){
        return roomTypes.stream().filter(c->c.getName()==id).findFirst().get();
    }

    public List<RoleBean> getRoles() {
        return roles;
    }


}
