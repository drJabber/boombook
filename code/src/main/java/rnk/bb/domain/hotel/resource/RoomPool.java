package rnk.bb.domain.hotel.resource;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    private String name;

    @NotNull
    @Min(0)
    @Column(columnDefinition = "numeric(15,2)")
    private Double basePrice=0.0;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms;

}
