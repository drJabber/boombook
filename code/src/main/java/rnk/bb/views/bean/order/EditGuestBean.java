package rnk.bb.views.bean.order;

import rnk.bb.domain.book.Order;
import rnk.bb.views.bean.hotel.EditFoodConceptBean;
import rnk.bb.views.bean.util.EditAddressBean;

import javax.enterprise.context.SessionScoped;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@SessionScoped
public class EditGuestBean implements Serializable {
    private Long id=null;

    private Order order=null;

    @Size(max=100)
    @Email(message = "Введите правильный email")
    private String email="";

    @Size(max=300)
    private String name="";

    private Date birthDate=null;

    @NotNull
    @Size(max=2)
    private String gender="М";

    private EditDocumentBean document=new EditDocumentBean();

    private EditFoodConceptBean foodConcept=new EditFoodConceptBean();

    private EditAddressBean address=new EditAddressBean();

    public EditGuestBean(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public EditAddressBean getAddress() {
        return address;
    }

    public void setAddress(EditAddressBean address) {
        this.address = address;
    }

    public EditDocumentBean getDocument() {
        return document;
    }

    public void setDocument(EditDocumentBean document) {
        this.document = document;
    }

    public EditFoodConceptBean getFoodConcept() {
        return foodConcept;
    }

    public void setFoodConcept(EditFoodConceptBean foodConcept) {
        this.foodConcept = foodConcept;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
