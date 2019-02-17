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
@Table(name="approval", schema = "public")
@EqualsAndHashCode(callSuper = false)
public class Approval extends AbstractEntity {
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="awaiting_hotel_id")
    private Hotel awaitingHotel;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="hotel_id")
    private Hotel hotel;

    private String someField;

    @Column(name="approvedState", columnDefinition = "integer default 0 ", nullable = false)
    private Integer approvedState=0;

    @Column(name = "approval_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvalDate=null;

    public Hotel getAwaitingHotel(){
        return awaitingHotel;
    }

    public void setAwaitingHotel(Hotel hotel){
        awaitingHotel=hotel;
    }

    public Hotel getHotel(){
        return hotel;
    }

    public void setHotel(Hotel hotel){
        this.hotel=hotel;
    }

    public Date getApprovalDate(){
        return approvalDate;
    }

    public void setApprovalDate(Date date){
        approvalDate=date;
    }

    public Integer getApprovedState(){
        return this.approvedState;
    }

    public void setApprovedState(Integer state){
        this.approvedState=state;
    }

    public String toString(){
        String result="Approval: ";
        if (getId()!=null){
            result.concat(getId().toString());
        }
        if (hotel!=null){
            result.concat(" hotel:");
            result.concat(hotel.getName());
        }
        if (awaitingHotel!=null){
            result.concat(" awaitinghotel:");
            result.concat(awaitingHotel.getName());
        }

        return result;
    }

//    public int hashCode(){
//        return getId().hashCode();
//    }
}
