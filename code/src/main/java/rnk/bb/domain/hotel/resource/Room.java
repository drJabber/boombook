package rnk.bb.domain.hotel.resource;

import lombok.Data;
import rnk.bb.domain.hotel.schedule.ScheduleItem;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Table(name="room", schema = "public")
public class Room {
    @Id
    @SequenceGenerator(name="room_id_seq",sequenceName = "room_id_seq",schema = "public",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="room_id_seq")
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="pool_id")
    private RoomPool pool;

    @NotNull
    @Size(max=10)
    @Column(nullable = false)
    private String number;

    @NotNull
    @Min(0)
    @Column(columnDefinition = "numeric(15,2)",nullable = false)
    private Double basePrice=0.0;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScheduleItem> scheduleItems;

}