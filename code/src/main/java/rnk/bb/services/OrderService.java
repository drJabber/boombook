package rnk.bb.services;

import rnk.bb.domain.book.Order;
import rnk.bb.domain.hotel.resource.FoodConcept;
import rnk.bb.domain.hotel.resource.Guest;
import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.domain.util.Address;
import rnk.bb.domain.util.Document;
import rnk.bb.rest.book.GuestController;
import rnk.bb.rest.book.OrderController;
import rnk.bb.rest.util.AddressController;
import rnk.bb.rest.util.DocumentController;
import rnk.bb.services.bean.CountryBean;
import rnk.bb.views.bean.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class OrderService implements Serializable {
    @Inject
    OrderController orders;

    @Inject
    GuestController guests;

    @Inject
    AddressController addresses;
    
    @Inject
    DocumentController documents;

    @Inject
    CacheService cache;

    @Inject
    HotelService hotelService;

    public Order findById(Long id){
        return this.orders.findOptionalById(id).orElseThrow(()->new HotelNotFoundException(id));
    }

    public EditOrderBean initOrderBean(EditOrderBean orderBean, Long orderId){
        Order order=null;
        if (orderId!=null){
            order=orders.findOptionalById(orderId).orElse(null);
        }

        return initOrderBean(orderBean,order);
    }

    public EditOrderBean initOrderBean(EditOrderBean orderBean, Order order){
        if ((orderBean!=null) && (order!=null)){
            orderBean.setClient(order.getClient());
            orderBean.setId(order.getId());
            orderBean.setEmail(order.getEmail());
            orderBean.setPhone(order.getPhone());
            orderBean.setPrice(order.getPrice());
            orderBean.setSubmitted(order.getSubmitted());
            orderBean.setConfirmed(order.getConfirmed());
            orderBean.setRejected(order.getRejected());
            orderBean.setCheckInTime(order.getCheckInTime());
            orderBean.setCheckOutTime(order.getCheckOutTime());

            List<EditGuestBean> guests=orderBean.getGuests();
            guests.clear();
            order.getGuests().stream().forEach(g->guests.add(initGuestBean(new EditGuestBean(),g)));
        }
        return orderBean;
    }

    public EditGuestBean initGuestBean(EditGuestBean guestBean, Long guestId){
        Guest guest=null;
        if (guestId!=null){
            guest=guests.findOptionalById(guestId).orElse(null);
        }
        EditGuestBean result=guestBean;
        if (guestBean==null)
        {
            result=new EditGuestBean();
        }
        return initGuestBean(result,guest);
    }

    private EditGuestBean cleanGuestBean(EditGuestBean guestBean){

        guestBean.setOrder(null);
        guestBean.setName("");
        guestBean.setId(null);
        guestBean.setEmail("");
        guestBean.setGender("М");
        guestBean.setBirthDate(null);
        initAddressBean(guestBean.getAddress(),(EditAddressBean)null);
        initDocumentBean(guestBean.getDocument(),(EditDocumentBean)null);
        hotelService.initFoodConceptBean(guestBean.getFoodConcept(),(EditFoodConceptBean)null);

        return guestBean;
    }

    public EditGuestBean initGuestBean(EditGuestBean guestBean, Guest guest){
        if (guest!=null){
            guestBean.setOrder(guest.getOrder());
            guestBean.setId(guest.getId());
            guestBean.setBirthDate(guest.getBirthDate());
            guestBean.setName(guest.getName());
            guestBean.setGender(guest.getGender());
            guestBean.setEmail(guest.getEmail());

            initAddressBean(guestBean.getAddress(), guest.getAddress());
            initDocumentBean(guestBean.getDocument(),guest.getDocument());
            hotelService.initFoodConceptBean(guestBean.getFoodConcept(),guest.getFoodConcept());

            return guestBean;
        }else{
            return cleanGuestBean(guestBean);
        }
    }

    public EditGuestBean initGuestBean(EditGuestBean guestBean, EditGuestBean anotherBean){
        if ((guestBean!=null) && (anotherBean!=null)){
            guestBean.setId(anotherBean.getId());
            guestBean.setName(anotherBean.getName());
            guestBean.setGender(anotherBean.getGender());
            guestBean.setBirthDate(anotherBean.getBirthDate());
            guestBean.setEmail(anotherBean.getEmail());
            initAddressBean(guestBean.getAddress(),anotherBean.getAddress());
            initDocumentBean(guestBean.getDocument(),anotherBean.getDocument());
            hotelService.initFoodConceptBean(guestBean.getFoodConcept(),anotherBean.getFoodConcept());

            return guestBean;
        }else{
            return cleanGuestBean(guestBean);
        }
    }

    public EditRoomOrderBean initRoomOrderBean(EditRoomOrderBean roomOrderBean, Long roomOrderId){
        RoomOrder roomOrder=null;
        if (roomOrderId!=null){
            roomOrder=roomOrders.findOptionalById(roomOrderId).orElse(null);
        }
        EditRoomOrderBean result=roomOrderBean;
        if (roomOrderBean==null)
        {
            result=new EditRoomOrderBean();
        }
        return initRoomOrderBean(result,roomOrder);
    }

    private EditRoomOrderBean cleanRoomOrderBean(EditRoomOrderBean roomOrderBean){

        roomOrderBean.setOrder(null);
        roomOrderBean.setId(null);
        roomOrderBean.setCheckInDate(null);
        roomOrderBean.setCheckOutDate(null);
        hotelService.initRoomPoolBean(roomOrderBean.getRoomPool(),(EditRoomPoolBean)null);
        
        roomOrderBean.getFeatures().clear();
        return roomOrderBean;
    }

    public EditRoomOrderBean initRoomOrderBean(EditRoomOrderBean roomOrderBean, RoomOrder roomOrder){
        if (roomOrder!=null){
            roomOrderBean.setOrder(roomOrder.getOrder());
            roomOrderBean.setId(roomOrder.getId());
            roomOrderBean.setCheckInDate(roomOrder.getCheckInTime());
            roomOrderBean.setCheckOutDate(roomOrder.getCheckOutTime());
            hotelService.initRoomPoolBean(roomOrderBean.getRoomPool(),roomOrder.getRoomPool());

            List<EditRoomFeatureBean> features=roomOrderBean.getFeatures();
            features.clear();
            roomOrder.getFeatures().stream().forEach(f->features.add(initRoomFeatureBean(new EditRoomFeatureBean(),f)));

            return roomOrderBean;
        }else{
            return cleanRoomOrderBean(roomOrderBean);
        }
    }

    public EditRoomOrderBean initRoomOrderBean(EditRoomOrderBean roomOrderBean, EditRoomOrderBean anotherBean){
        if ((roomOrderBean!=null) && (anotherBean!=null)){
            roomOrderBean.setOrder(anotherBean.getOrder());
            roomOrderBean.setId(anotherBean.getId());
            roomOrderBean.setCheckInDate(anotherBean.getCheckInDate());
            roomOrderBean.setCheckOutDate(anotherBean.getCheckOutDate());
            hotelService.initRoomPoolBean(roomOrderBean.getRoomPool(),anotherBean.getRoomPool());

            List<EditRoomFeatureBean> features=roomOrderBean.getFeatures();
            features.clear();
            anotherBean.getFeatures().stream().forEach(f->features.add(initRoomFeatureBean(new EditRoomFeatureBean(),f)));
            
            return roomOrderBean;
        }else{
            return cleanRoomOrderBean(roomOrderBean);
        }
    }

    public EditAddressBean initAddressBean(EditAddressBean addressBean, Long addressId){
        Address address=null;
        if (addressId!=null){
            address=addresses.findOptionalById(addressId).orElse(null);
        }

        return initAddressBean(addressBean,address);
    }

    private EditAddressBean cleanAddressBean(EditAddressBean addressBean){
        addressBean.setId(null);
        addressBean.setZip("");
        addressBean.setCountryId(643);
        addressBean.setCountry("Россия");
        addressBean.setSettlementPart("");
        addressBean.setStreetPart("");
        return addressBean;
    }

    public EditAddressBean initAddressBean(EditAddressBean addressBean, Address address){
        if (address!=null) {
            addressBean.setId(address.getId());

            addressBean.setCountryId(address.getCountry().getId());
            addressBean.setCountry(address.getCountry().getNameRu());
            addressBean.setZip(address.getZip());
            addressBean.setSettlementPart(address.getSettlementPart());
            addressBean.setStreetPart(address.getStreetPart());

            return addressBean;
        }else {
            return cleanAddressBean(addressBean);
        }
    }

    public EditAddressBean initAddressBean(EditAddressBean addressBean, EditAddressBean anotherBean){
        if ((addressBean!=null)&&(anotherBean!=null)) {
            addressBean.setId(anotherBean.getId());

            addressBean.setCountryId(anotherBean.getCountryId());
            CountryBean country=cache.getCountryById(anotherBean.getCountryId());
            addressBean.setCountry(country.getName());
            addressBean.setZip(anotherBean.getZip());
            addressBean.setSettlementPart(anotherBean.getSettlementPart());
            addressBean.setStreetPart(anotherBean.getStreetPart());

            return addressBean;
        }else{
            return cleanAddressBean(addressBean);
        }
    }


    private EditDocumentBean cleanDocumentbean(EditDocumentBean documentBean){
        documentBean.setDocumentType("ПАСПОРТ");
        documentBean.setId(null);
        documentBean.setSerial("");
        documentBean.setNumber("");
        documentBean.setIssueDate(null);
        documentBean.setExpirationDate(null);

        return documentBean;
    }

    public EditDocumentBean initDocumentBean(EditDocumentBean documentBean, Long documentId){
        Document document =null;
        if (documentId!=null){
            document=documents.findOptionalById(documentId).orElse(null);
        }
        return initDocumentBean(documentBean,document);
    }

    public EditDocumentBean initDocumentBean(EditDocumentBean documentBean, Document document){
        if (document!=null) {
            documentBean.setId(document.getId());

            documentBean.setDocumentType(document.getDocumentType().getDescription());
            documentBean.setNumber(document.getNumber());
            documentBean.setSerial(document.getSerial());
            documentBean.setIssueDate(document.getIssueDate());
            documentBean.setExpirationDate(document.getExpirationDate());
            return documentBean;
        }else {
            return cleanDocumentbean(documentBean);
        }
    }

    public EditDocumentBean initDocumentBean(EditDocumentBean documentBean, EditDocumentBean anotherBean){
        if ((documentBean!=null)&&(anotherBean!=null)) {
            documentBean.setId(anotherBean.getId());

            documentBean.setDocumentType(anotherBean.getDocumentType());
            documentBean.setNumber(anotherBean.getNumber());
            documentBean.setSerial(anotherBean.getSerial());
            documentBean.setIssueDate(anotherBean.getIssueDate());
            documentBean.setExpirationDate(anotherBean.getExpirationDate());
            return documentBean;
        }else{
            return cleanDocumentbean(documentBean);
        }
    }

}
