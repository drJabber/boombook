package rnk.bb.domain.hotel.resource;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name="room_type", schema = "public")
public class RoomType {
    @Id
    @SequenceGenerator(name="room_type_id_seq",sequenceName = "room_type_id_seq",schema = "public",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="room_type_id_seq")
    private Integer id;

    @NotNull
    @Size(max=200)
    private String name;

    @NotNull
    @Size(max=500)
    private String description;
}
