package rnk.bb.views.bean.hotel;


import lombok.Data;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;

@Data
@SessionScoped
public class ApprovalBean implements Serializable {
    private Long id;

    private EditHotelBean hotel=null;

    private EditHotelBean awaitingHotel=new EditHotelBean();

    private Integer approvedState=0;

    private Date approvalDate;

    public ApprovalBean(){

    }
}
