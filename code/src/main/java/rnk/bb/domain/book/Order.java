package rnk.bb.domain.book;

import lombok.Data;
import lombok.EqualsAndHashCode;
import rnk.bb.domain.blank.AbstractEntity;
import rnk.bb.domain.blank.AbstractHotelRefEntity;
import rnk.bb.domain.hotel.resource.Guest;
import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.domain.user.Client;
import rnk.bb.util.json.DateAdapter;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="order", schema = "public")
@EqualsAndHashCode(callSuper = false)
public class Order  extends AbstractHotelRefEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="client_id")
    private Client client;

    @NotNull
    @Size(max=100)
    @Column(nullable = false)
    private String email;

    @NotNull
    @Size(max=100)
    @Column(nullable = false)
    private String phone;

    @NotNull
    @Column(nullable = false)
    private Boolean submitted=false;

    @NotNull
    @Min(0)
    @Column(columnDefinition = "numeric(15,2)",nullable = false)
    private Double price=0.0;

    @NotNull
    @Column(nullable = false)
    private Boolean confirmed=false;

    @NotNull
    @Column(nullable = false)
    private Boolean rejected=false;

    @Temporal(TemporalType.DATE)
    @JsonbTypeAdapter(DateAdapter.class)
    Date checkInTime;

    @Temporal(TemporalType.DATE)
    @JsonbTypeAdapter(DateAdapter.class)
    Date checkOutTime;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<RoomOrder> roomOrders;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Guest> guests;
}
