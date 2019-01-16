package rnk.bb.domain.hotel.resource;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name="order", schema = "public")
public class Order {
    @Id
    @SequenceGenerator(name="order_id_seq",sequenceName = "order_id_seq",schema = "public",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="order_id_seq")
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="hotel_id")
    private Hotel hotel;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="client_id")
    private Client client;

    @NotNull
    @Size(max=100)
    private String email;

    @NotNull
    @Size(max=100)
    private String phone;

    @NotNull
    private Boolean submitted=false;

    @NotNull
    @Min(0)
    @Column(columnDefinition = "numeric(15,2)")
    private Double price=0.0;

    @NotNull
    private Boolean confirmed=false;

    @NotNull
    private Boolean rejected=false;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomOrder> roomOrders;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Guest> guests;
}
