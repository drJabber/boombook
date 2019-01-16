package rnk.bb.domain.book;

import lombok.Data;
import rnk.bb.domain.hotel.resource.RoomFeature;
import rnk.bb.domain.hotel.resource.RoomPool;
import rnk.bb.helper.json.DateAdapter;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import rnk.bb.domain.book.Order;

@Data
@Entity
@Table(name="room_order", schema = "public")
public class RoomOrder {
    @Id
    @SequenceGenerator(name="room_order_id_seq",sequenceName = "room_order_id_seq",schema = "public",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="room_order_id_seq")
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="order_id")
    private Order order;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="pool_id")
    private RoomPool pool;

    @NotNull
    @Temporal(TemporalType.DATE)
    @JsonbTypeAdapter(DateAdapter.class)
    Date checkInTime;

    @NotNull
    @Temporal(TemporalType.DATE)
    @JsonbTypeAdapter(DateAdapter.class)
    Date checkOutTime;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "roomOrders", fetch = FetchType.LAZY)
    private Set<RoomFeature> features=new HashSet<>();
}
