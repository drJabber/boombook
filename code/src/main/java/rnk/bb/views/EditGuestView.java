package rnk.bb.views;

import org.primefaces.PrimeFaces;
import rnk.bb.domain.hotel.resource.Guest;
import rnk.bb.rest.book.GuestController;
import rnk.bb.views.bean.EditGuestBean;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;

@Named("editGuestView")
@ViewScoped()
public class EditGuestView implements Serializable {
    private Long guestId;

    private Guest guest;

    @Inject
    GuestController guests;

    @Inject
    EditGuestBean guestBean;


    @PostConstruct
    public void init(){
        Map<String, String> params= FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (params.get("id")!=null){
            this.guestId=Long.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
            guest=this.guests.findOptionalById(guestId).orElse(null);
        }
        guestBean.init(guest);
    }


    public EditGuestBean getGuest(){
        return guestBean;
    }

    public void addGuestFromDialog(){
        PrimeFaces.current().dialog().closeDynamic(guest);
    }

    public void closeDialog(){
        PrimeFaces.current().dialog().closeDynamic(null);
    }
}
