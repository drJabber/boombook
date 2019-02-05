package rnk.bb.rest.util;

import rnk.bb.domain.util.Address;
import rnk.bb.domain.util.Country;
import rnk.bb.rest.blank.CustomController;
import rnk.bb.views.bean.order.EditAddressBean;

import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Startup
@DependsOn({"StartupController"})
@Path("v1")
public class AddressController extends CustomController<Address, Long> {

    @Inject
    CountryController countries;

    public Address createAddress(EditAddressBean addressBean){
        Address address=new Address();
        Country country=countries.findByIdCached(addressBean.getCountryId());
        address.setCountry(country);
        address.setStreetPart(addressBean.getStreetPart());
        address.setSettlementPart(addressBean.getSettlementPart());
        address.setZip(addressBean.getZip());
        return address;
    }

    @PUT
    @Path("util/adr")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(JsonObject info) {
        return saveInternal(info);
    }

    @POST
    @Path("util/adr")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(JsonObject info) {
        return saveInternal(info);
    }

    @GET
    @Path("util/adr/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") Long addressId) {
        return readInternal(addressId);
    }

    @DELETE
    @Path("util/adr/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response delete(@PathParam("id") Long addressId) {
        return deleteInternal(addressId);
    }
}
