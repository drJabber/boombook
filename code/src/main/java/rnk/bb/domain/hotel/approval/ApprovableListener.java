package rnk.bb.domain.hotel.approval;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class ApprovableListener {
    @PrePersist
    public void SetApprovalOnCreate(ApprovableInterface approvable){
        Approval approval=approvable.getApproval();
        if (approval==null){
            approval=new Approval();
            approvable.setApproval(approval);
        }

        approval.setApprovalDate(null);
    }

    @PreUpdate
    public void SetApprovalOnUpdate(ApprovableInterface approvable){
        Approval approval=approvable.getApproval();

        approval.setApprovalDate(null);
    }

}
