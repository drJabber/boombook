package rnk.bb.views;

import org.primefaces.model.DualListModel;
import rnk.bb.services.HotelService;
import rnk.bb.services.OrderService;
import rnk.bb.views.bean.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("editOrderView")
@SessionScoped
public class EditOrderView implements Serializable {
    private static Logger log=Logger.getLogger(EditOrderView.class.getName());

    private Long orderId=null;
    private Long guestId=null;
    private Long hotelId=null;

    private String state="order";

    @Inject
    private HotelService hotelService;

    @Inject
    private OrderService orderService;

    private EditHotelBean hotelBean;

    private EditOrderBean orderBean;

    private EditGuestBean guestBean;

    private EditRoomOrderBean roomBean;

    private DualListModel<EditRoomFeatureBean> roomFeatures;



    @PostConstruct
    public void init(){
        Map<String, String> params= FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        if (params.get("hotelId")!=null){
            hotelId= Long.valueOf(params.get("hotelId"));
        }

        if (params.get("orderId")!=null){
            this.orderId=Long.valueOf(params.get("orderId"));
        }

        if (params.get("guestId")!=null){
            this.guestId=Long.valueOf(params.get("guestId"));
        }

        hotelBean=new EditHotelBean();
        hotelService.initHotelBean(hotelBean,hotelId);

        orderBean=new EditOrderBean();
        orderService.initOrderBean(orderBean,orderId);

        guestBean=new EditGuestBean();
        orderService.initGuestBean(guestBean,guestId);

        roomFeatures=new DualListModel<>(new ArrayList<>(), new ArrayList<>());
        initFeatures();
    }


    private void initFeatures(){
        List<EditRoomFeatureBean> source=roomFeatures.getSource();
        List<EditRoomFeatureBean> dest=roomFeatures.getTarget();
        source.clear();
        dest.clear();
        if (roomBean!=null){
            List<EditRoomFeatureBean> features=roomBean.getFeatures();
            features.stream().forEach(f->source.add(hotelService.initRoomFeatureBean(new EditRoomFeatureBean(),f)));
            hotelBean.getRoomFeatures()
                    .stream()
                    .filter(f->!features.stream().anyMatch(x->x.getId().equals(f.getId())))
                    .forEach(f->source.add(hotelService.initRoomFeatureBean(new EditRoomFeatureBean(),f)));
        }else {
            hotelBean.getRoomFeatures().stream().forEach(f->source.add(hotelService.initRoomFeatureBean(new EditRoomFeatureBean(),f)));
        }
    }

    public EditOrderBean getOrderBean(){
        return orderBean;
    }

    public String getState(){
        return state;
    }


    public EditGuestBean getGuestBean(){
        return guestBean;
    }

    public void setGuestBean(EditGuestBean guestBean){
        this.guestBean=guestBean;
    }

    public void addGuest(){
        log.log(Level.INFO,"add new guest");
        EditGuestBean bean=new EditGuestBean();
        this.guestBean=orderService.initGuestBean(bean,(EditGuestBean) null);
        state="guest";
    }

    public void editGuest(){
        log.log(Level.INFO, String.format("edit guest %s", guestBean.getName()));
//        this.guestBean=orderService.initGuestBean(guestBean,(EditGuestBean) null);
        state="guest";
    }

    public void removeGuest(){
        log.log(Level.INFO, String.format("remove guest %s", guestBean.getName()));
        orderBean.getGuests().remove(guestBean);
//        this.guestBean=orderService.initGuestBean(guestBean,(EditGuestBean) null);
        state="order";
    }

    public void saveGuest(EditGuestBean guestBean){
        log.log(Level.INFO,"save guest");
        if (!orderBean.getGuests().contains(guestBean)){
            orderBean.getGuests().add(orderService.initGuestBean(new EditGuestBean(),guestBean));
        }
        state="order";
    }

    public void cancelGuest(){
        log.log(Level.INFO,"cancel guest");
        state="order";
    }

    public void setGuestAddress(EditGuestBean guest){
        log.log(Level.INFO,"add new guest");
        state="guest-address";
    }

    public void saveGuestAddress(EditGuestBean guest){
        log.log(Level.INFO,"save guest address");
//        guest.getAddress().init(guest.getAddress());
        state="guest";
    }

    public void cancelGuestAddress(){
        log.log(Level.INFO,"cancel guest address");
        state="guest";
    }


    public void saveGuestDocument(EditGuestBean guest){
        log.log(Level.INFO,"save guest document");
//        this.guestBean.getDocument().init(guest.getDocument());
        state="guest";
    }

    public void cancelGuestDocument(){
        log.log(Level.INFO,"cancel guest document");
        state="guest";
    }

    public void setGuestDocument(EditGuestBean guest){
        log.log(Level.INFO,"add new document");
//        this.guestBean.getDocument().init(guest.getDocument());
        state="guest-document";
    }

    public EditHotelBean getHotelBean() {
        return hotelBean;
    }

    public void setHotelBean(EditHotelBean hotelBean) {
        this.hotelBean = hotelBean;
    }

    public void addRoom(){
        log.log(Level.INFO,"add new room order");
        EditRoomOrderBean bean=new EditRoomOrderBean();
        this.roomBean=orderService.initRoomOrderBean(bean,(EditRoomOrderBean) null);
        this.roomFeatures.setTarget(roomBean.getFeatures());
        state="room-order";
    }

    public void editRoom(){
        log.log(Level.INFO, String.format("edit room order %s", roomBean.toString()));
        state="room-order";
    }

    public void removeRoom(){
        log.log(Level.INFO, String.format("remove room order %s", roomBean.toString()));
        orderBean.getRooms().remove(roomBean);
        state="order";
    }

    public void saveRoom(EditRoomOrderBean roomBean){
        log.log(Level.INFO,"save room order");
        if (!orderBean.getRooms().contains(roomBean)){
            orderService.saveRoomOrderFeatures(roomBean,roomFeatures.getTarget());
            orderBean.getRooms().add(orderService.initRoomOrderBean(new EditRoomOrderBean(),roomBean));
        }
        state="order";
    }

    public void cancelRoom(){
        log.log(Level.INFO,"cancel room order");
        state="order";
    }


    public EditRoomOrderBean getRoomBean() {
        return roomBean;
    }

    public void setRoomBean(EditRoomOrderBean roomBean) {
        this.roomBean = roomBean;
    }

    public DualListModel<EditRoomFeatureBean> getRoomFeatures() {
        return roomFeatures;
    }

    public void setRoomFeatures(DualListModel<EditRoomFeatureBean> roomFeatures) {
        this.roomFeatures = roomFeatures;
    }
}
