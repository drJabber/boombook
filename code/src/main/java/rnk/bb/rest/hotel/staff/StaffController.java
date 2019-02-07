package rnk.bb.rest.hotel.staff;


import rnk.bb.domain.auth.Auth;
import rnk.bb.domain.hotel.staff.Staff;
import rnk.bb.domain.user.Client;
import rnk.bb.domain.util.Address;
import rnk.bb.domain.util.Document;
import rnk.bb.rest.auth.AuthController;
import rnk.bb.rest.blank.CustomController;
import rnk.bb.views.bean.registration.RegUserBean;
import rnk.bb.views.bean.registration.StaffUserBean;

import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Startup
@DependsOn({"StartupController"})
@Path("v1")
public class StaffController extends CustomController<Staff, Long> {

    @Inject
    AuthController auths;

    private Staff createStaff(StaffUserBean staffBean){
        Staff staff=new Staff();

        staff.setName(staffBean.getName());
        staff.setGender(staffBean.getGender());
        staff.setBirthDate(staffBean.getBirthDate());

        Auth auth=auths.createUser(staffBean);

        staff.setLogin(auth);

        return staff;
    }

    public void registerStaff(StaffUserBean staffBean){
        EntityManager em=entityManager();

        Staff staff=createStaff(staffBean);
        em.merge(staff);
    }



    @PUT
    @Path("hotel/staff")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(JsonObject info) {
        return saveInternal(info);
    }

    @POST
    @Path("hotel/staff")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(JsonObject info) {
        return saveInternal(info);
    }

    @GET
    @Path("hotel/staff/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") Long staffId) {
        return readInternal(staffId);
    }

    @DELETE
    @Path("hotel/staff/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response delete(@PathParam("id") Long staffId) {
        return deleteInternal(staffId);
    }
}
