package rnk.bb.rest.hotel.approval;

import rnk.bb.domain.hotel.approval.Approval;
import rnk.bb.rest.blank.CustomController;
import rnk.bb.rest.util.HotelSearchCriteria;
import rnk.bb.rest.util.ServerUtils;
import rnk.bb.views.bean.hotel.ApprovalBean;

import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@Startup
@DependsOn({"StartupController"})
@Path("v1")
public class approvalController extends CustomController<Approval, Long> {
    private static Logger log= Logger.getLogger(approvalController.class.getName());

    @PUT
    @Path("hotel/approval/approval")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(JsonObject info) {
        return saveInternal(info);
    }

    @POST
    @Path("hotel/approval/approval")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(JsonObject info) {
        return saveInternal(info);
    }

    @GET
    @Path("hotel/approval/approval{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") Long approvalId) {
        return readInternal(approvalId);
    }

    @DELETE
    @Path("hotel/approval/approval{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response delete(@PathParam("id") Long approvalId) {
        return deleteInternal(approvalId);
    }


    @Inject
    ServerUtils serverUtils;

    public void updateApprovalAtate(ApprovalBean approvalBean){
        EntityManager em=entityManager();

        Approval approval=findById(approvalBean.getId());
        approval.setApprovedState(approvalBean.getApprovedState());

        em.merge(approval);
    }

    public List<Approval> searchApprovedHotels(HotelSearchCriteria criteria){
        log.log(Level.INFO,"search approved hotel...");

        String query="from Approval a " +
                "left join Hotel ah  on h=a.awaitingHotel " +
                "left join Hotel h on h=a.hotel where a.approvedState=1 ";

        String countQuery="select count(a) ";

        List<Approval> approvals=new ArrayList<>();

        try{
            StringBuilder sb=new StringBuilder(query);

            countQuery+=sb.toString();
            sb=sb.append("order by a.approvalDate asc");


            Query q=entityManager().createQuery(countQuery);
            int resultSize=((Long)q.getSingleResult()).intValue();
            criteria.setResultSize(resultSize);

            if (resultSize!=0){
                int firstResult=criteria.getFirstResult();
                int pageSize=criteria.getPageSize();

                firstResult=serverUtils.computeFirstResult(firstResult,pageSize,resultSize);

                Query qc=entityManager().createQuery(sb.toString());
                qc.setFirstResult(firstResult);
                qc.setMaxResults(pageSize);

                approvals=qc.getResultList();
            }
        }catch(Exception ex){
            log.log(Level.SEVERE, ex.getMessage());
        }

        return approvals;
    }

}

