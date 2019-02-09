package rnk.bb.domain.hotel.resource;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;
import rnk.bb.domain.blank.AbstractEntity;
import rnk.bb.domain.book.Order;
import rnk.bb.domain.hotel.approval.Approval;
import rnk.bb.domain.hotel.schedule.ScheduleItem;
import rnk.bb.domain.hotel.staff.Staff;
import rnk.bb.domain.util.Address;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name="hotel", schema = "public")
public class Hotel extends AbstractEntity {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="manager_id")
    private Staff manager;

    @NotNull
    @Size(max=500)
    @Column(nullable = false)
    private String name;

    @Size(max = 100)
    @Column(nullable = true)
    private String email;

    @Size(max = 1024)
    @Column(nullable = true)
    private String place;

    @Size(max=1024)
    @Column(nullable = true)
    private String site;

    @Size(max=100)
    @Column(nullable = true)
    private String phone;

    @Size(max=100)
    @Column(nullable = true)
    private String fax;

    @Size(max=1024)
    @Column(nullable = true)
    private String descr;

    @Size(max = 1024)
    @Column(nullable = true)
    private String vk;

    @Size(max = 1024)
    @Column(nullable = true)
    private String fb;

    @Lob
    @Basic(fetch=FetchType.LAZY)
    private String longDescr;

    @NotNull
    @Column(nullable = false)
    private Boolean published=false;

    @NotNull
    @Column(nullable = false)
    @Range(min=0, max=7)
    private Integer stars=5;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="address_id")
    private Address address;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="approval_id")
    private Approval approval;

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
    private List<RoomPool> roomPools;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScheduleItem> scheduleItems;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

}
