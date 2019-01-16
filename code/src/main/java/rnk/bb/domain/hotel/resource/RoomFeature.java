package rnk.bb.domain.hotel.resource;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name="room_feature", schema = "public")
public class RoomFeature {
    @Id
    @SequenceGenerator(name="room_feature_id_seq",sequenceName = "room_feature_id_seq",schema = "public",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="room_feature_id_seq")
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="hotel_id")
    private Hotel hotel;

    @NotNull
    @Size(max=200)
    private String name;

    @NotNull
    @Min(0)
    @Column(columnDefinition = "numeric(15,2)")
    private Double price=0.0;

    @NotNull
    @Size(max=500)
    private String description;
}
