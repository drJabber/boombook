package rnk.bb.domain.hotel.resource;


import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name="hotel_payment_policy", schema = "public")
public class HotelPaymentPolicy {
    @Id
    @SequenceGenerator(name="hotel_payment_policy_id_seq",sequenceName = "hotel_payment_policy__id_seq",schema = "public",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="hotel_payment_policy__id_seq")
    private Integer id;

    @NotNull
    @Range(min = 0, max=100)
    @Column(columnDefinition = "numeric(15,2)")
    private Double prePayPercent=0.0;
}
