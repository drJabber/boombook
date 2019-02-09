package rnk.bb.domain.hotel.approval;

import lombok.Data;
import lombok.EqualsAndHashCode;
import rnk.bb.domain.blank.AbstractEntity;
import rnk.bb.domain.blank.AbstractHotelRefEntity;
import rnk.bb.domain.hotel.resource.Hotel;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="staff", schema = "public")
@EqualsAndHashCode(callSuper = false)
public class Approval extends AbstractEntity {
    @Id
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="awaiting_hotel_id")
    private Hotel awaitingHotel;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="hotel_id")
    private Hotel Hotel;

    private Boolean state=false;

    @Column(name = "approval_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvalDate=null;
}
