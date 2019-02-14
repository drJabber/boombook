package rnk.bb.services;

import rnk.bb.domain.auth.Auth;
import rnk.bb.domain.book.Order;
import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.domain.hotel.staff.Staff;
import rnk.bb.rest.hotel.staff.StaffController;
import rnk.bb.services.auth.LoginService;
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
    HotelService hotelService;

    public Staff findByLogin(String login){
        return this.staff.findByLogin(login);
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
        return initStaffBean(result,staff);
    }


    private StaffUserBean cleanStaffBean(StaffUserBean staffBean){
        staffBean.setStaffId((null));
        staffBean.setLogin("");
        hotelService.initHotelBean(staffBean.getHotel(),(Hotel) null);
        staffBean.setName("");
        staffBean.setBirthDate(null);
        staffBean.setGender("М");

        return staffBean;
    }

    public StaffUserBean initStaffBean(StaffUserBean staffBean, Staff staff){
        if (staff!=null) {
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



}
