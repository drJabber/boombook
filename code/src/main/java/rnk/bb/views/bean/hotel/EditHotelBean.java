package rnk.bb.views.bean.hotel;

import lombok.Data;
import rnk.bb.views.bean.registration.StaffUserBean;
import rnk.bb.views.bean.util.EditAddressBean;

import javax.enterprise.context.SessionScoped;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@SessionScoped
public class EditHotelBean implements Serializable {
    private Long id=null;

    @Size(max=500)
    private String name="";

    @Size(max = 100)
    private String email="";

    @Size(max = 1024)
    private String place="";

    @Size(max=1024)
    private String site="";

    @Size(max=100)
    private String phone="";

    @Size(max=100)
    private String fax="";

    @Size(max=1024)
    private String descr="";

    @Size(max = 1024)
    private String vk="";

    @Size(max = 1024)
    private String fb="";

    private String longDescr="";

    @NotNull
    private Boolean published=false;

    @NotNull
    private Integer stars=5;

    @NotNull
    private EditAddressBean address=new EditAddressBean();

    private EditPaymentPolicyBean paymentPolicy=new EditPaymentPolicyBean();

    private List<EditFoodConceptBean> foodConcepts=new ArrayList<>();
    private List<EditRoomFeatureBean> roomFeatures=new ArrayList<>();
    private List<EditRoomPoolBean> roomPools=new ArrayList<>();

    public EditHotelBean(){

    }

    private StaffUserBean manager=null;

}
