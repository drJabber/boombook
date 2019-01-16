package rnk.bb.domain.hotel.resource;

import lombok.Data;
import rnk.bb.domain.book.Order;
import rnk.bb.domain.hotel.schedule.ScheduleItem;
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
    @Column(nullable = false)
    private String name;

    @NotNull
    @Size(max = 100)
    @Column(nullable = false)
    private String email;

    @NotNull
    @Size(max=1024)
    @Column(nullable = false)
    private String site;

    @NotNull
    @Size(max=100)
    @Column(nullable = false)
    private String phone;

    @NotNull
    @Column(nullable = false)
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

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

}
