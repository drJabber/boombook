package rnk.bb.domain.hotel.resource;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="address_id")
    private Address address;

    @OneToOne
    @JoinColumn(name="document_id")
    private Document document;

    @OneToOne
    @JoinColumn(name="document_id")
    private FoodConcept foodConcept;
}
