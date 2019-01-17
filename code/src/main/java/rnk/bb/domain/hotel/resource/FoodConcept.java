package rnk.bb.domain.hotel.resource;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name="food_concept", schema = "public")
public class FoodConcept {
    @Id
    @SequenceGenerator(name="food_concept_id_seq",sequenceName = "food_concept_id_seq",schema = "public",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="food_concept_id_seq")
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="hotel_id")
    private Hotel hotel;

    @NotNull
    @Size(max=200)
    @Column(nullable = false)
    private String name;

    @NotNull
    @Min(0)
    @Column(columnDefinition = "numeric(15,2)", nullable = false)
    private Double basePrice=0.0;

    @NotNull
    @Size(max=500)
    @Column(nullable = false)
    private String description;
}