package rnk.bb.services;


import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import rnk.bb.domain.hotel.approval.Approval;
import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.rest.hotel.approval.approvalController;
import rnk.bb.rest.util.HotelSearchCriteria;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

@Named(value = "hotelApprovalService")
@ApplicationScoped
public class ApprovalService implements Serializable {
    private static Logger log=Logger.getLogger(ApprovalService.class.getName());
    @Inject
    approvalController approvals;

    private LazyDataModel<Approval> approvalsLazy;
    private HotelSearchCriteria criteria;



    @PostConstruct
    private void init(){
        approvalsLazy=new LazyDataModel<Approval>() {
            public List<Approval> load(int first, int pageSize, String sortField,
                                    SortOrder sortOrder, java.util.Map<String, Object> filters) {
                List<Approval> result=null;
                criteria=new HotelSearchCriteria();

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


}

