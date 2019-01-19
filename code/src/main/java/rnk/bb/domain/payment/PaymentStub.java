package rnk.bb.domain.payment;

import lombok.Data;
import rnk.bb.domain.blank.AbstractEntity;
import rnk.bb.domain.book.Order;
import rnk.bb.util.json.DateAdapter;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Table(name="payment", schema = "public")
public class PaymentStub extends AbstractEntity {
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
