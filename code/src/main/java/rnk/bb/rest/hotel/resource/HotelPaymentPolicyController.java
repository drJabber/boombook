package rnk.bb.rest.hotel.resource;

import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.domain.hotel.resource.HotelPaymentPolicy;
import rnk.bb.rest.blank.CustomController;
import rnk.bb.views.bean.hotel.EditPaymentPolicyBean;

import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Startup
@DependsOn({"StartupController"})
@Path("v1")
public class HotelPaymentPolicyController   extends CustomController<HotelPaymentPolicy, Long> {

    public HotelPaymentPolicy createOrUpdatePaymentPolicy(EditPaymentPolicyBean policyBean){
        HotelPaymentPolicy policy=null;
        if (policyBean.getId()!=null){
            policy=findByLongId(policyBean.getId());
        }
        if (policy!=null){
            return updatePaymentPolicy(policy, policyBean);
        }else {
            return createPaymentPolicy(policyBean);
        }
    }

    public HotelPaymentPolicy createPaymentPolicy(EditPaymentPolicyBean policyBean){
        HotelPaymentPolicy policy=new HotelPaymentPolicy();
        return updatePaymentPolicy(new HotelPaymentPolicy(),policyBean);
    }

    public HotelPaymentPolicy updatePaymentPolicy(HotelPaymentPolicy policy, EditPaymentPolicyBean policyBean){
        policy.setPrePayPercent(policyBean.getPrePayPercent());
        return policy;
    }



    @PUT
    @Path("hotel/resource/pp")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(JsonObject info) {
        return saveInternal(info);
    }

    @POST
    @Path("hotel/resource/pp")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(JsonObject info) {
        return saveInternal(info);
    }

    @GET
    @Path("hotel/resource/pp/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") Long policyId) {
        return readInternal(policyId);
    }

    @DELETE
    @Path("hotel/resource/pp/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response delete(@PathParam("id") Long policyId) {
        return deleteInternal(policyId);
    }
}
