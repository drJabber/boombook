package rnk.bb.domain.blank;

import lombok.Data;
import rnk.bb.domain.hotel.resource.Hotel;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public class AbstractHotelRefEntity extends AbstractEntity{
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="hotel_id")
    private Hotel hotel;

}
