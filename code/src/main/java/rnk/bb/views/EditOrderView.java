package rnk.bb.views;

import org.primefaces.model.DualListModel;
import rnk.bb.services.HotelService;
import rnk.bb.services.OrderService;
import rnk.bb.views.bean.hotel.EditHotelBean;
import rnk.bb.views.bean.order.*;

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

//    @Inject
//    Conversation conversation;

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

        if (hotelBean==null){
            hotelBean=new EditHotelBean();
            hotelService.initHotelBean(hotelBean,hotelId);
        }


        if (orderBean==null){
            orderBean=new EditOrderBean();
            orderService.initOrderBean(orderBean,orderId);
        }

        if (guestBean==null){
            guestBean=new EditGuestBean();
            orderService.initGuestBean(guestBean,guestId);
        }

        if (roomFeatures==null){
            roomFeatures=new DualListModel<>(new ArrayList<>(), new ArrayList<>());
            initFeatures(roomBean);
        }
    }

    private void initFeatures(EditRoomOrderBean room){
        List<EditRoomFeatureBean> source=roomFeatures.getSource();
        List<EditRoomFeatureBean> dest=roomFeatures.getTarget();
        source.clear();
        dest.clear();
        if (room!=null){
            List<EditRoomFeatureBean> features=room.getFeatures();
            features.stream().forEach(f->dest.add(hotelService.initRoomFeatureBean(new EditRoomFeatureBean(),f)));
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

    public void removeGuest(EditGuestBean currentGuest){
        log.log(Level.INFO, String.format("remove guest %s", currentGuest.getName()));
        orderBean.getGuests().remove(currentGuest);
//        this.guestBean=orderService.initGuestBean(guestBean,(EditGuestBean) null);
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
        state="guest";
    }

    public void cancelGuestAddress(){
        log.log(Level.INFO,"cancel guest address");
        state="guest";
    }


    public void saveGuestDocument(EditGuestBean guest){
        log.log(Level.INFO,"save guest document");
        state="guest";
    }

    public void cancelGuestDocument(){
        log.log(Level.INFO,"cancel guest document");
        state="guest";
    }

    public void setGuestDocument(EditGuestBean guest){
        log.log(Level.INFO,"add new document");
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
        this.initFeatures(roomBean);
        state="room-order";
    }

    public void removeRoom(EditRoomOrderBean currentRoom){
        log.log(Level.INFO, String.format("remove room order %s", currentRoom.toString()));
        orderBean.getRooms().remove(currentRoom);
        state="order";
    }

    public void editGuest(EditGuestBean currentGuest){
        log.log(Level.INFO, String.format("edit guest %s", currentGuest.getName()));
        state="guest";
    }

    public void editRoom(EditRoomOrderBean currentRoom){
        log.log(Level.INFO, String.format("edit room order %s", currentRoom.toString()));
        initFeatures(currentRoom);
        state="room-order";
    }

    public void saveGuest(EditGuestBean currentGuest){
        log.log(Level.INFO,"save guest");
        if (!orderBean.getGuests().contains(guestBean)){
            guestBean=orderService.initGuestBean(new EditGuestBean(),currentGuest);
            orderBean.getGuests().add(guestBean);
        }
        state="order";
    }

    public void saveRoom(EditRoomOrderBean currentRoom){
        log.log(Level.INFO,"save room order");
        orderService.updateRoomOrderFeatures(roomBean,roomFeatures.getTarget());
        if (!orderBean.getRooms().contains(currentRoom)){
            orderBean.getRooms().add(orderService.initRoomOrderBean(new EditRoomOrderBean(),currentRoom));
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

    public void clean(){
        init();
    }
}
