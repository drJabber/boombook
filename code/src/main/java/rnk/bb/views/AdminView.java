package rnk.bb.views;

import org.primefaces.model.LazyDataModel;
import rnk.bb.domain.hotel.approval.Approval;
import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.services.ApprovalService;
import rnk.bb.services.HotelService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("adminView")
@ViewScoped
public class AdminView implements Serializable {
    private static Logger log=Logger.getLogger(AdminView.class.getName());

    @Inject
    private ApprovalService approvalService;


    private Approval selectedApproval;
    private LazyDataModel<Approval> approvalsRequested;

    @PostConstruct
    public void init(){
        log.log(Level.INFO,"adminview started...");
        this.approvalsRequested=approvalService.getApprovalsLazy();
    }


    public void update(){
        init();
    }

    public String getApproveHotelHeaderTitle(){
        return "Подтверждение отелей";
    }

    public void approveHotel(){
        log.log(Level.INFO,"approve hotel");
        approvalService.approveHotel(selectedApproval);

    }

    public void rejectHotel(){
        log.log(Level.INFO,"reject hotel");
        approvalService.rejectHotel(selectedApproval);
    }


    public Approval getSelectedApproval(){
        return selectedApproval;
    }

    public void setSelectedApproval(Approval approval){
        this.selectedApproval=approval;
    }

    public LazyDataModel<Approval> getApprovals(){
        return approvalsRequested ;
    }
}
