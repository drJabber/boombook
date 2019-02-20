package rnk.bb.services;

import rnk.bb.domain.hotel.approval.Approval;
import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.domain.hotel.staff.Staff;
import rnk.bb.rest.hotel.approval.approvalController;
import rnk.bb.rest.hotel.resource.HotelController;
import rnk.bb.rest.hotel.staff.StaffController;
import rnk.bb.views.bean.hotel.EditHotelBean;
import rnk.bb.views.bean.registration.StaffUserBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("staffService")
@ApplicationScoped
public class StaffService implements Serializable {
    private static Logger log=Logger.getLogger(StaffService.class.getName());

    @Inject
    StaffController staff;

    @Inject
    HotelController hotels;

    @Inject
    approvalController approvals;

    @Inject
    HotelService hotelService;

    @Inject
    StaffService staffService;

    public Staff findByLogin(String login){
        Staff s=this.staff.findByLogin(login);
        return s;
    }

    public StaffUserBean initStaffBean(StaffUserBean staffBean, Long staffId){
        Staff staff=null;
        if (staffId!=null){
            staff=this.staff.findOptionalById(staffId).orElse(null);
        }
        StaffUserBean result=staffBean;
        if (staffBean==null)
        {
            result=new StaffUserBean();
        }
        return initStaffBean(result,staff,false);
    }


    private StaffUserBean cleanStaffBean(StaffUserBean staffBean){
        staffBean.setStaffId((null));
        staffBean.setLogin("");
        hotelService.initHotelBean(staffBean.getHotel(),(Hotel) null);
        staffBean.setName("");
        staffBean.setBirthDate(null);
        staffBean.setGender("М");

        hotelService.initApprovalBean(staffBean.getApproval(),(Approval)null,(EditHotelBean)null);

        return staffBean;
    }

    public StaffUserBean initStaffBean(StaffUserBean staffBean, Staff staff, Boolean resetApproval){
        if (staff!=null) {
            staffBean.setStaffId(staff.getId());
            staffBean.setLogin(staff.getLogin().getLogin());

            Approval approval=null;
            if (!resetApproval){
                approval=staff.getApproval();
            }

            hotelService.initApprovalBean(staffBean.getApproval(),approval, staffBean.getHotel());
            if (staff.hasAwaitingHotel() && !resetApproval){
                hotelService.initAwaitingHotelBean(staffBean.getHotel(), staff.getApproval().getAwaitingHotel());
            }else{
                hotelService.initHotelBean(staffBean.getHotel(), staff.getHotel());
            }

            staffBean.setName(staff.getName());
            staffBean.setBirthDate(staff.getBirthDate());
            staffBean.setGender(staff.getGender());

            return staffBean;
        }else{
            return cleanStaffBean(staffBean);
        }
    }

    public StaffUserBean initStaffBean(StaffUserBean staffBean, StaffUserBean anotherBean){
        if ((staffBean!=null)&&(anotherBean!=null)) {
            Staff staff=this.staff.findByLongId(anotherBean.getStaffId());

            staffBean.setStaffId(staff.getId());
            staffBean.setLogin(staff.getLogin().getLogin());
            hotelService.initHotelBean(staffBean.getHotel(), staff.getHotel());

            staffBean.setName(staff.getName());
            staffBean.setBirthDate(staff.getBirthDate());
            staffBean.setGender(staff.getGender());

            return staffBean;
        }else{
            return cleanStaffBean(staffBean);
        }
    }


    public Boolean doSaveStaff(StaffUserBean user, Boolean isManager){
        log.log(Level.INFO, "save hotel staff");
        staff.saveStaff(user,isManager);

        return true;
    }

    public void publishHotel(EditHotelBean hotelBean, StaffUserBean staffBean){
        if ((hotelBean!=null) && (staffBean!=null)){
            Hotel hotel = hotelService.findById(staffBean.getHotel().getId());
            Staff staff = this.staff.findById(staffBean.getStaffId());
            hotels.updateHotel(hotel,hotelBean);
            hotel.setPublished(true);
            hotel.setApproval(null);
            hotels.save(hotel);
        }
    }




}
