package rnk.bb.domain.hotel.resource;

import lombok.Data;
import rnk.bb.domain.hotel.staff.Staff;
import rnk.bb.domain.util.Address;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Table(name="hotel", schema = "public")
public class Hotel {
    @Id
    @SequenceGenerator(name="hotel_id_seq",sequenceName = "hotel_id_seq",schema = "public",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="hotel_id_seq")
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="manager_id")
    private Staff manager;

    @NotNull
    @Size(max=500)
    private String name;

    @NotNull
    @Size(max = 100)
    private String email;

    @NotNull
    @Size(max=1024)
    private String site;

    @NotNull
    @Size(max=100)
    private String phone;

    @NotNull
    private Boolean published=false;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="address_id")
    private Address address;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="policy_id")
    private HotelPaymentPolicy paymenPpolicy;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Staff> staffList;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodConcept> foodConcepts;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomFeature> roomFeatures;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScheduleItem> scheduleItems;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

}
