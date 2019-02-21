package rnk.bb.services;


import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import rnk.bb.domain.hotel.approval.Approval;
import rnk.bb.rest.hotel.approval.ApprovalController;
import rnk.bb.rest.util.HotelSearchCriteria;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named(value = "hotelApprovalService")
@SessionScoped
public class ApprovalService implements Serializable {
    private static Logger log=Logger.getLogger(ApprovalService.class.getName());

    @Inject
    private ApprovalController approvals;

    private LazyDataModel<Approval> approvalsLazy;
    private HotelSearchCriteria criteria;

    @PostConstruct
    private void init(){
        log.log(Level.INFO,"approvalservice started...");
        this.criteria=new HotelSearchCriteria();

        approvalsLazy=new LazyDataModel<Approval>() {
            public List<Approval> load(int first, int pageSize, String sortField,
                                    SortOrder sortOrder, java.util.Map<String, Object> filters) {
                log.log(Level.INFO,"approvalservice load called...");
                List<Approval> result=null;

                criteria.setFirstResult(first);
                criteria.setPageSize(pageSize);
                criteria.setSortField(sortField);
                criteria.setAscending(SortOrder.ASCENDING.equals(sortOrder));

                result= approvals.searchApprovedHotels(criteria);
                this.setRowCount(criteria.getResultSize());

                return result;
            }

        };
    }

    public void resetSearchCriteria(){
        criteria=new HotelSearchCriteria();
    }

    public HotelSearchCriteria getSearchCriteria(){
        return criteria;
    }

    public LazyDataModel<Approval> getApprovalsLazy(){
        return this.approvalsLazy;
    }

    public void approveHotel(Approval approval){
        approval.setApprovedState(2);
        approvals.updateApproval(approval);
    }

    public void rejectHotel(Approval approval){
        approval.setApprovedState(0);
        approvals.updateApproval(approval);
    }
}

