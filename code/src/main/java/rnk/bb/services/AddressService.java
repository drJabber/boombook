package rnk.bb.services;

import rnk.bb.domain.util.Address;
import rnk.bb.rest.util.AddressController;
import rnk.bb.services.bean.CountryBean;
import rnk.bb.views.bean.util.EditAddressBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;

@ApplicationScoped
public class AddressService implements Serializable {
    @Inject
    CacheService cache;

    @Inject
    AddressController addresses;

    public EditAddressBean initAddressBean(EditAddressBean addressBean, Long addressId){
        Address address=null;
        if (addressId!=null){
            address=addresses.findOptionalById(addressId).orElse(null);
        }

        return initAddressBean(addressBean,address);
    }

    public Address createAddress(EditAddressBean addressBean){
        return addresses.createAddress(addressBean);
    }

    public  Address createOrUpdateAddress(EditAddressBean addressBean){
        return addresses.createOrUpdateAddress(addressBean);
    }

    public EditAddressBean cleanAddressBean(EditAddressBean addressBean){
        addressBean.setId(null);
        addressBean.setZip("");
        addressBean.setCountryId(643);
        addressBean.setCountry("Россия");
        addressBean.setSettlementPart("");
        addressBean.setStreetPart("");
        return addressBean;
    }

    public EditAddressBean initAddressBean(EditAddressBean addressBean, Address address){
        if (address!=null) {
            addressBean.setId(address.getId());
            if (address.getCountry()!=null){
                addressBean.setCountryId(address.getCountry().getId());
                addressBean.setCountry(address.getCountry().getNameRu());
            }else{
                addressBean.setCountryId(null);
                addressBean.setCountry(null);
            }
            addressBean.setZip(address.getZip());
            addressBean.setSettlementPart(address.getSettlementPart());
            addressBean.setStreetPart(address.getStreetPart());

            return addressBean;
        }else {
            return cleanAddressBean(addressBean);
        }
    }

    public EditAddressBean initAddressBean(EditAddressBean addressBean, EditAddressBean anotherBean){
        if ((addressBean!=null)&&(anotherBean!=null)) {
            addressBean.setId(anotherBean.getId());

            addressBean.setCountryId(anotherBean.getCountryId());
            CountryBean country=cache.getCountryById(anotherBean.getCountryId());
            addressBean.setCountry(country.getName());
            addressBean.setZip(anotherBean.getZip());
            addressBean.setSettlementPart(anotherBean.getSettlementPart());
            addressBean.setStreetPart(anotherBean.getStreetPart());

            return addressBean;
        }else{
            return cleanAddressBean(addressBean);
        }
    }
}
