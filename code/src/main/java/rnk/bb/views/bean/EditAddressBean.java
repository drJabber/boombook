package rnk.bb.views.bean;

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

    private void init(EditAddressBean addressBean){
        if (addressBean!=null){
            this.id=addressBean.getId();
            
            this.countryId=addressBean.getCountryId();
            this.country=addressBean.getCountry();
            this.zip=addressBean.getZip();
            this.settlementPart=addressBean.getSettlementPart();
            this.streetPart=addressBean.streetPart();
        }else{
            this.id=null;
            this.countryId=null;
            this.country="";
            this.zip=null;
            this.settlementPart="";
            this.streetPart="";
        }
    }

    public void init(Address address){
        if (address!=null){
            this.id=address.getId();
            
            this.countryId=address.getCountry().getId();
            this.country=address.getCountry().getName_ru();
            this.zip=address.getZip();
            this.settlementPart=address.getSettlementPart();
            this.streetPart=address.streetPart();
        }else{
            this.id=null;
            this.countryId=null;
            this.country="";
            this.zip=null;
            this.settlementPart="";
            this.streetPart="";
        }
    }

    public void updateAddress(Address address){
        Guest result=guest;
        if (guest==null){
            result=new Guest();
        }
        result.setOrder(order);
        result.setId(id);
        result.setName(name);
        result.setBirthDate(birthDate);
        result.setGender(gender);
        result.setEmail(email);

        address.updateAddress(guest.getAddress());
//        result.setDocument(document);
//        result.setFoodConcept(foodConcept);
    }

    

}
