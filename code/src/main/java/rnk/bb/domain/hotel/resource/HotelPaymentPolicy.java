package rnk.bb.domain.hotel.resource;


import lombok.Data;
import org.hibernate.validator.constraints.Range;
import rnk.bb.domain.blank.AbstractEntity;

import javax.persistence.*;
import javax.validation.Constraint;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name="hotel_payment_policy", schema = "public")
public class HotelPaymentPolicy extends AbstractEntity {
    @NotNull
    @Range(min = 0, max=100)
    @Column(columnDefinition = "numeric(15,2)",nullable = false)
    private Double prePayPercent=0.0;
}
