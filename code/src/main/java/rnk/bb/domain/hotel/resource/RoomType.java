package rnk.bb.domain.hotel.resource;

import lombok.Data;
import lombok.EqualsAndHashCode;
import rnk.bb.domain.blank.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name="room_type", schema = "public")
public class RoomType  extends AbstractEntity {
    @NotNull
    @Size(max=200)
    @Column(nullable = false)
    private String name;

    @NotNull
    @Size(max=500)
    @Column(nullable = false)
    private String description;
}
