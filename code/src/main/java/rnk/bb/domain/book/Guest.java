package rnk.bb.domain.hotel.resource;

import lombok.Data;
import rnk.bb.domain.util.Address;
import rnk.bb.domain.util.Document;
import rnk.bb.helper.json.DateAdapter;
import rnk.bb.domain.book.Order;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


@Data
@Entity
@Table(name="guest", schema = "public")
public class Guest {
    @Id
    @SequenceGenerator(name="guest_id_seq",sequenceName = "guest_id_seq",schema = "public",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="guest_id_seq")
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="order_id")
    private Order order;

    @NotNull
    @Size(max=300)
    private String name;

    @NotNull
    @Temporal(TemporalType.DATE)
    @JsonbTypeAdapter(DateAdapter.class)
    private Date birthDate;

    @NotNull
    @Size(max=2)
    private String gender;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name="address_id")
    private Address address;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name="document_id")
    private Document document;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name="food_concept_id")
    private FoodConcept foodConcept;
}
