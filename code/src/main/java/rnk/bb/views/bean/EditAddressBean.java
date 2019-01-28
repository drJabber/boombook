package rnk.bb.views.bean;

import rnk.bb.domain.util.Address;
import rnk.bb.domain.util.Country;

import javax.enterprise.context.SessionScoped;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@SessionScoped
public class EditAddressBean implements Serializable {
    private Long id=null;

    @NotNull
    private Integer countryId;

    @NotNull
    @Size(max=500)
    private String country;

    @NotNull
    @Size(max=20)
    private String zip="";

    @NotNull
    @Size(max=500)
    private String settlementPart="";

    @NotNull
    @Size(max=500)
    private String streetPart="";

    public EditAddressBean(){        
    }

    public EditAddressBean(EditAddressBean addressBean){
        init(addressBean);
    }

    public EditAddressBean(Address address){
        init(address);
    }

    private void clean(){
        this.id=null;
        this.countryId=null;
        this.country="";
        this.zip=null;
        this.settlementPart="";
        this.streetPart="";
    }

    public void init(EditAddressBean addressBean){
        if (addressBean!=null){
            this.id=addressBean.getId();
            
            this.countryId=addressBean.getCountryId();
            this.country=addressBean.getCountry();
            this.zip=addressBean.getZip();
            this.settlementPart=addressBean.getSettlementPart();
            this.streetPart=addressBean.getStreetPart();
        }else{
            clean();
        }
    }

    public void init(Address address){
        if (address!=null){
            this.id=address.getId();
            
            this.countryId=address.getCountry().getId();
            this.country=address.getCountry().getNameRu();
            this.zip=address.getZip();
            this.settlementPart=address.getSettlementPart();
            this.streetPart=address.getStreetPart();
        }else{
            clean();
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountry() {
        CountryBean country=cacheService.getCountryById(countryId);
        return country.getName();
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getSettlementPart() {
        return settlementPart;
    }

    public void setSettlementPart(String settlementPart) {
        this.settlementPart = settlementPart;
    }

    public String getStreetPart() {
        return streetPart;
    }

    public void setStreetPart(String streetPart) {
        this.streetPart = streetPart;
    }

    public String toString(){
        StringBuilder sb=new StringBuilder();
        List<String> list=new ArrayList();
        String country=getCountry();
        
        if (!zip.trim().isEmpty()){
            list.add(zip.trim());
        }

        if (!country.trim().isEmpty()){
            list.add(country.trim());
        }

        if (!settlementPart.trim().isEmpty()){
            list.add(settlementPart.trim());
        }

        if (!streetPart.trim().isEmpty()){
            list.add(streetPart.trim());
        }

        return list.stream().collect(Collectors.joining(","))
    }
}
