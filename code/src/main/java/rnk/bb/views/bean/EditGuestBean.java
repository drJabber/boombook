package rnk.bb.views.bean;

import rnk.bb.domain.book.Order;
import rnk.bb.domain.hotel.resource.FoodConcept;
import rnk.bb.domain.hotel.resource.Guest;
import rnk.bb.domain.util.Address;
import rnk.bb.domain.util.Document;

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

    private Address address=null;

    private Document document=null;

    private FoodConcept foodConcept=null;

    public EditGuestBean(){

    }

    public EditGuestBean(Guest guest){
        init(guest);
    }

    public void init(Guest guest){
        if (guest!=null){
            this.order=guest.getOrder();

            this.id=guest.getId();
            this.name=guest.getName();
            this.birthDate=guest.getBirthDate();
            this.gender=guest.getGender();
            this.email=guest.getEmail();

//            this.address=guest.getAddress();
//            this.document=guest.getDocument();
//            this.foodConcept=guest.getFoodConcept();
        }else{
            this.order=null;
            this.id=null;
            this.name="";
            this.birthDate=null;
            this.gender="М";
            this.email="";
        }
    }

    public void updateGuest(Guest guest){
        Guest result=guest;
        if (guest==null){
            result=new Guest();
        }
        result.setOrder(order);
        result.setId(id);
        result.setName(name);
        result.setBirthDate(birthDate);
        result.setGender(gender);
        result.setEmail(email);

//        result.setAddress(address);
//        result.setDocument(document);
//        result.setFoodConcept(foodConcept);
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public FoodConcept getFoodConcept() {
        return foodConcept;
    }

    public void setFoodConcept(FoodConcept foodConcept) {
        this.foodConcept = foodConcept;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
