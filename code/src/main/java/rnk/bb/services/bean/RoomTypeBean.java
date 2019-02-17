package rnk.bb.services.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import rnk.bb.domain.hotel.resource.RoomType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class RoomTypeBean {
    Long id;

    @NotNull
    @Size(max=200)
    private String name;

    @NotNull
    @Size(max=500)
    private String description;

    public RoomTypeBean(RoomType roomType){
        this.setId(roomType.getId());
        this.name=roomType.getName();
        this.description=roomType.getDescription();
    }

    public String toString(){
        return name;
    }
}
