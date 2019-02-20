package rnk.bb.views;

import org.primefaces.model.LazyDataModel;
import rnk.bb.domain.hotel.approval.Approval;
import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.services.ApprovalService;
import rnk.bb.services.HotelService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("adminView")
@SessionScoped
public class AdminView implements Serializable {
    private static Logger log=Logger.getLogger(AdminView.class.getName());

    @Inject
    ApprovalService approvalService;


    private Hotel selectedHotel;
    private LazyDataModel<Approval> approvalsRequested;

    @PostConstruct
    public void init(){
        log.log(Level.INFO,"adminview started...");
        this.approvalsRequested=approvalService.getApprovalsLazy();
    }


    public void update(){

    }

    public String getApproveHotelHeaderTitle(){
        return "Подтверждение отелей";
    }

    public void approveHotel(){
        log.log(Level.INFO,"approve hotel");

    }

    public void rejectHotel(){
        log.log(Level.INFO,"reject hotel");
    }


    public Hotel getSelectedHotel(){
        return selectedHotel;
    }

    public LazyDataModel<Approval> getApprovals(){
        return approvalsRequested;
    }
}
