package rnk.bb.services;

import rnk.bb.domain.book.Order;
import rnk.bb.domain.hotel.staff.Staff;
import rnk.bb.rest.hotel.staff.StaffController;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("staffService")
@ApplicationScoped
public class StaffService {

    @Inject
    StaffController staff;

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
        return initFoodConceptBean(result,staff);
    }


    private EditFoodConceptBean cleanStaffBean(StaffUserBean staffBean){
        staffBean.setId(null);
        staffBean.setLogin(null);
        staffBean.setHotel(null);
        staffBean.setName("");
        staffBean.setBirthDate(null);
        staffBean.setGender("М");
        staffBean.setApproved(false);

        return fcBean;
    }

    public StaffUserBean initStaffBean(StaffUserBean staffBean, Staff staff){
        if (staff!=null) {
            staffBean.setId(staff.getId());
            staffBean.setLogin(staff.getLogin());
            staffBean.setHotel(staff.getHotel());
            staffBean.setName(staff.getName());
            staffBean.setBirthDate(staff.getBirthDate());
            staffBean.setGender(staff.getGender());
            staffBean.setApproved(false);

            return staffBean;
        }else{
            return cleanStaffBean(staffBean);
        }
    }

    public StaffUserBean initStaffBean(StaffUserBean staffBean, StaffUserBean anotherBean){
        if ((staffBean!=null)&&(anotherBean!=null)) {
            Staff staff=this.staff.findByLongId(anotherBean.getId());

            staffBean.setId(staff.getId());
            staffBean.setLogin(staff.getLogin());
            staffBean.setHotel(staff.getHotel());
            staffBean.setName(staff.getName());
            staffBean.setBirthDate(staff.getBirthDate());
            staffBean.setGender(staff.getGender());
            staffBean.setApproved(false);

            return staffBean;
        }else{
            return cleanStaffBean(staffBean);
        }
    }

    

}
