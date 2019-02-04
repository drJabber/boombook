package rnk.bb.services.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import rnk.bb.domain.hotel.resource.RoomType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class RoleBean implements Serializable {
    @NotNull
    @Size(max=100)
    private String role;

    public RoleBean(String role){
        this.role=role;
    }
}
