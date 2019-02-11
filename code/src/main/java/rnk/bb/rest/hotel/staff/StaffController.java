package rnk.bb.rest.hotel.staff;


import com.sun.org.apache.xpath.internal.operations.Bool;
import rnk.bb.domain.auth.Auth;
import rnk.bb.domain.hotel.approval.Approval;
import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.domain.hotel.staff.Staff;
import rnk.bb.rest.auth.AuthController;
import rnk.bb.rest.blank.CustomController;
import rnk.bb.rest.hotel.resource.HotelController;
import rnk.bb.views.bean.hotel.EditHotelBean;
import rnk.bb.views.bean.registration.StaffUserBean;

import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Singleton
@Startup
@DependsOn({"StartupController"})
@Path("v1")
public class StaffController extends CustomController<Staff, Long> {

    @Inject
    AuthController auths;

    @Inject
    HotelController hotels;

    private Staff createStaff(StaffUserBean staffBean, Boolean isManager){
        Staff staff=new Staff();

        staff.setName(staffBean.getName());
        staff.setGender(staffBean.getGender());
        staff.setBirthDate(staffBean.getBirthDate());

        Auth auth=auths.createUser(staffBean);

        EditHotelBean hotelBean=staffBean.getHotel();
        if (hotelBean !=null ){
            Hotel hotel=hotels.findByLongId(hotelBean.getId());
            if (isManager){
                Hotel awaitingHotel=hotels.createHotel(hotelBean);
                createApproval(awaitingHotel, hotel,staff);
            }
            staff.setHotel(hotel);
        }

        staff.setLogin(auth);

        return staff;
    }

    private void createApproval(Hotel awaitingHotel,Hotel hotel, Staff staff){
        Approval approval=new Approval();
        awaitingHotel.setManager(staff);
        awaitingHotel.setApproval(approval);
        approval.setAwaitingHotel(awaitingHotel);
        approval.setHotel(hotel);
    }

    public void registerStaff(StaffUserBean staffBean){
        EntityManager em=entityManager();

        Staff staff=createStaff(staffBean, false);
        em.merge(staff);
    }

    public void registerManager(StaffUserBean staffBean){
        EntityManager em=entityManager();

        Staff staff=createStaff(staffBean, true);
        em.merge(staff);
    }

    public Staff findByLogin(String login){
        CriteriaBuilder cb = this.entityManager().getCriteriaBuilder();
        CriteriaQuery<Staff> q = cb.createQuery(Staff.class);

        Root<Staff> root = q.from(Staff.class);
        Join<Staff,Auth> authJoin=root.join("login", JoinType.INNER);
        List<Predicate> predicates=new ArrayList<>();
        predicates.add(cb.equal(authJoin.get("login"),login));
        q.select(root).where(predicates.toArray(new Predicate[0]));

        return entityManager().createQuery(q).getSingleResult();
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
