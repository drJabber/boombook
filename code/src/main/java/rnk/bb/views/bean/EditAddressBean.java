package rnk.bb.views.bean;

import javax.enterprise.context.SessionScoped;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@SessionScoped
public class EditAddressBean implements Serializable {
    private Long id=null;

    @NotNull
    private Integer countryId;

    @NotNull
    @Size(max=500)
    private String country;

    @NotNull
    @Size(max=20)
    private String zip="";

    @NotNull
    @Size(max=500)
    private String settlementPart="";

    @NotNull
    @Size(max=500)
    private String streetPart="";

}
