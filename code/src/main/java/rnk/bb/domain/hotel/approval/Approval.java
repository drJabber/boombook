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
public class Approval extends AbstractHotelRefEntity {
    @Id
    private Long id;

    @OneToOne
    private Hotel awaitingHotel;

    private Boolean state;

    @Column(name = "approval_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvalDate;
}
