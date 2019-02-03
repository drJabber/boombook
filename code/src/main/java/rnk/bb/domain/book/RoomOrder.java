package rnk.bb.domain.book;

import lombok.Data;
import lombok.EqualsAndHashCode;
import rnk.bb.domain.blank.AbstractEntity;
import rnk.bb.domain.hotel.resource.RoomFeature;
import rnk.bb.domain.hotel.resource.RoomPool;
import rnk.bb.util.json.DateAdapter;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name="room_order", schema = "public")
public class RoomOrder  extends AbstractEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="order_id")
    private Order order;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="pool_id")
    private RoomPool pool;

    @Temporal(TemporalType.DATE)
    @JsonbTypeAdapter(DateAdapter.class)
    Date checkInTime;

    @Temporal(TemporalType.DATE)
    @JsonbTypeAdapter(DateAdapter.class)
    Date checkOutTime;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "roomOrders", fetch = FetchType.LAZY)
    private Set<RoomFeature> features=new HashSet<>();
}
