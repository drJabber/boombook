package rnk.bb.domain.hotel.resource;

import lombok.Data;
import rnk.bb.domain.book.RoomOrder;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name="room_feature", schema = "public")
public class RoomFeature {
    @Id
    @SequenceGenerator(name="room_feature_id_seq",sequenceName = "room_feature_id_seq",schema = "public",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="room_feature_id_seq")
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="hotel_id",nullable = false)
    private Hotel hotel;

    @NotNull
    @Size(max=200)
    @Column(nullable = false)
    private String name;

    @NotNull
    @Min(0)
    @Column(columnDefinition = "numeric(15,2)",nullable = false)
    private Double price=0.0;

    @NotNull
    @Size(max=500)
    @Column(nullable = false)
    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<RoomOrder> roomOrders=new HashSet<>();

}
