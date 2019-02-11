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


}
