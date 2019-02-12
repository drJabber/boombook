package rnk.bb.views.bean.registration;

import rnk.bb.services.bean.RoleBean;
import rnk.bb.views.bean.hotel.EditHotelBean;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Named("staffUserBean")
@SessionScoped
public class StaffUserBean implements Serializable {

    private Long staffId=null;

    @NotNull
    private String name="";

    private Date birthDate=null;

    @NotNull
    @Size(max=2)
    private String gender="М";

    @NotNull
    @Size(max=100)
    @Email(message = "Введите правильный email")
    private String email="";

    @NotNull
    @Size(max=100)
    private String phone="";

    @NotNull
    private String login="";

    @NotNull
    private String password="";

    private EditHotelBean hotel=new EditHotelBean();

    @NotNull
    private RoleBean role;


    public StaffUserBean(){

    }

    public RoleBean getRole() {
        return role;
    }

    public void setRole(RoleBean role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public StaffUserBean(String role_name)
    {
        role=new RoleBean(role_name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public EditHotelBean getHotel() {
        return hotel;
    }

    public void setHotel(EditHotelBean hotel) {
        this.hotel = hotel;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }


}
