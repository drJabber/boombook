package rnk.bb.views.bean.hotel;


import lombok.Data;

import javax.enterprise.context.SessionScoped;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@SessionScoped
public class EditPaymentPolicyBean implements Serializable {

    private Long id;

    @NotNull
    private Double prePayPercent=0.;



}
