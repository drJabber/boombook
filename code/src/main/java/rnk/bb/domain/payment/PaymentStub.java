package rnk.bb.domain.payment;

import lombok.Data;
import rnk.bb.domain.book.Order;
import rnk.bb.helper.json.DateAdapter;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Table(name="payment", schema = "public")
public class PaymentStub {
    @Id
    @SequenceGenerator(name="payment_id_seq",sequenceName = "payment_id_seq",schema = "public",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="payment_id_seq")
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="order_id")
    private Order order;

    @NotNull
    @Column(columnDefinition = "numeric(15,2)",nullable = false)
    private Double sum=0.0;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @JsonbTypeAdapter(DateAdapter.class)
    @Column(nullable = false)
    private Date timestamp;
}
