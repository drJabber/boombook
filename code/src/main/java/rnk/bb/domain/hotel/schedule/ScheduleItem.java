package rnk.bb.domain.hotel.schedule;

import lombok.Data;
import lombok.EqualsAndHashCode;
import rnk.bb.domain.blank.AbstractHotelRefEntity;
import rnk.bb.domain.hotel.resource.Room;
import rnk.bb.util.json.DateAdapter;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Table(name="schedule_item", schema = "public")
@EqualsAndHashCode(callSuper = false)
public class ScheduleItem extends AbstractHotelRefEntity {
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
