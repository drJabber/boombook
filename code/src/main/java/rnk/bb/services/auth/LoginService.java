package rnk.bb.services.auth;


import rnk.bb.domain.auth.Auth;
import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.domain.hotel.staff.Staff;
import rnk.bb.rest.auth.AuthController;
import rnk.bb.views.bean.auth.SessionDataBean;
import rnk.bb.views.bean.registration.StaffUserBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named("loginService")
@ApplicationScoped
public class LoginService implements Serializable {

    @Inject
    AuthController users;

    public SessionDataBean findUser(String login){
        return new SessionDataBean(users.getAuth(login));
    }


    public SessionDataBean initAccountBean(SessionDataBean accountBean, String accountId){
        Auth auth=null;
        if (accountId!=null){
            auth=this.users.findOptionalById(accountId).orElse(null);
        }
        SessionDataBean result=accountBean;
        if (accountBean==null)
        {
            result=new SessionDataBean();
        }
        return initAccountBean(result,auth);
    }


    private SessionDataBean cleanAccountBean(SessionDataBean accountBean){
        accountBean.setLogin("");
        loginService.initAuthBean(staffBean.getLogin(),(Auth)null));
        hotelService.initHotelBean(staffBean.getHotel(),(Hotel) null);
        staffBean.setName("");
        staffBean.setBirthDate(null);
        staffBean.setGender("М");

        return staffBean;
    }

    public StaffUserBean initStaffBean(StaffUserBean staffBean, Staff staff){
        if (staff!=null) {
            staffBean.setStaffId(staff.getId());
            loginService.initAuthBean(staffBean.getLogin(),staff.getLogin());
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
            loginService.initAuthBean(staffBean.getLogin(),staff.getLogin());
            hotelService.initHotelBean(staffBean.getHotel(), staff.getHotel());

            staffBean.setName(staff.getName());
            staffBean.setBirthDate(staff.getBirthDate());
            staffBean.setGender(staff.getGender());

            return staffBean;
        }else{
            return cleanStaffBean(staffBean);
        }
    }

}
