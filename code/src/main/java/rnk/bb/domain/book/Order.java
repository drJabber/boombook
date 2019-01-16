package rnk.bb.domain.book;

import lombok.Data;
import rnk.bb.domain.hotel.resource.Guest;
import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.domain.user.Client;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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
    @Column(nullable = false)
    private String email;

    @NotNull
    @Size(max=100)
    @Column(nullable = false)
    private String phone;

    @NotNull
    @Column(nullable = false)
    private Boolean submitted=false;

    @NotNull
    @Min(0)
    @Column(columnDefinition = "numeric(15,2)",nullable = false)
    private Double price=0.0;

    @NotNull
    @Column(nullable = false)
    private Boolean confirmed=false;

    @NotNull
    @Column(nullable = false)
    private Boolean rejected=false;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<RoomOrder> roomOrders;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Guest> guests;
}
