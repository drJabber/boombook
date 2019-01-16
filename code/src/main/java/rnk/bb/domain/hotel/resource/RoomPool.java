package rnk.bb.domain.hotel.resource;

import lombok.Data;
import rnk.bb.domain.auth.Auth;
import rnk.bb.domain.book.RoomOrder;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name="room_pool", schema = "public")
public class RoomPool {
    @Id
    @SequenceGenerator(name="room_pool_id_seq",sequenceName = "room_pool_id_seq",schema = "public",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="room_pool_id_seq")
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="hotel_id")
    private Hotel hotel;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="room_type_id")
    private RoomType roomType;

    @NotNull
    @Size(max=100)
    @Column(nullable = false)
    private String name;

    @NotNull
    @Min(0)
    @Column(columnDefinition = "numeric(15,2)",nullable = false)
    private Double basePrice=0.0;

    @OneToMany(mappedBy = "pool", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms;

    @OneToMany(mappedBy = "pool", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomOrder> orders;



}
