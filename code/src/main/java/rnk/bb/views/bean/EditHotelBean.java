package rnk.bb.views.bean;

import javax.enterprise.context.SessionScoped;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SessionScoped
public class EditHotelBean implements Serializable {
    private Long id=null;

    @Size(max=500)
    private String name="";

    private List<EditFoodConceptBean> foodConcepts=new ArrayList<>();
    private List<EditRoomFeatureBean> roomFeatures=new ArrayList<>();
    private List<EditRoomPoolBean> roomPools=new ArrayList<>();

    public EditHotelBean(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EditFoodConceptBean> getFoodConcepts() {
        return foodConcepts;
    }

    public void setFoodConcepts(List<EditFoodConceptBean> roomFeatures) {
        this.foodConcepts = foodConcepts;
    }

    public List<EditRoomFeatureBean> getRoomFeatures() {
        return roomFeatures;
    }

    public void setRoomFeatures(List<EditRoomFeatureBean> roomFeatures) {
        this.roomFeatures = roomFeatures;
    }

    public List<EditRoomPoolBean> getRoomPools() {
        return roomPools;
    }

    public void setRoomPools(List<EditRoomPoolBean> roomPools) {
        this.roomPools = roomPools;
    }

}
