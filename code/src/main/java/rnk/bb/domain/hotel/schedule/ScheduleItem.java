package rnk.bb.domain.hotel.schedule;

import lombok.Data;
import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.domain.hotel.resource.Room;
import rnk.bb.helper.json.DateAdapter;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@Table(name="schedule_item", schema = "public")
public class ScheduleItem {
    @Id
    @SequenceGenerator(name="schedule_item_id_seq",sequenceName = "schedule_item_id_seq",schema = "public",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="schedule_item_id_seq")
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="hotel_id")
    private Hotel hotel;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="room_id")
    private Room room;

    @NotNull
    @Temporal(TemporalType.DATE)
    @JsonbTypeAdapter(DateAdapter.class)
    @Column(nullable = false)
    Date startPeriod;

    @NotNull
    @Temporal(TemporalType.DATE)
    @JsonbTypeAdapter(DateAdapter.class)
    @Column(nullable = false)
    Date endPeriod;

}
