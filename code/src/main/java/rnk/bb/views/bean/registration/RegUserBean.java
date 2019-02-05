package rnk.bb.views.bean.registration;

import rnk.bb.services.bean.RoleBean;
import rnk.bb.views.bean.order.EditAddressBean;
import rnk.bb.views.bean.order.EditDocumentBean;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Named("regUserBean")
@SessionScoped
public class RegUserBean implements Serializable {

    Integer clientId;

    @NotNull
    private String name="";

    private Date birthDate=null;

    @NotNull
    @Size(max=2)
    private String gender="М";

    private EditDocumentBean document=new EditDocumentBean();

    private EditAddressBean address=new EditAddressBean();

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

    @NotNull
    private RoleBean role=new RoleBean("client");


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

    public RegUserBean()
    {

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

    public EditDocumentBean getDocument() {
        return document;
    }

    public void setDocument(EditDocumentBean document) {
        this.document = document;
    }

    public EditAddressBean getAddress() {
        return address;
    }

    public void setAddress(EditAddressBean address) {
        this.address = address;
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
}
