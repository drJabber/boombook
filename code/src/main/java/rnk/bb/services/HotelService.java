package rnk.bb.services;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import rnk.bb.domain.hotel.approval.Approval;
import rnk.bb.domain.hotel.resource.*;
import rnk.bb.rest.hotel.approval.ApprovalController;
import rnk.bb.rest.hotel.resource.FoodConceptController;
import rnk.bb.rest.hotel.resource.HotelController;
import rnk.bb.rest.hotel.resource.RoomFeatureController;
import rnk.bb.rest.hotel.resource.RoomPoolController;
import rnk.bb.rest.util.HotelSearchCriteria;
import rnk.bb.views.bean.hotel.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

@Named(value = "hotelService")
@ApplicationScoped
public class HotelService implements Serializable {
    private static Logger log=Logger.getLogger(HotelService.class.getName());

    @Inject
    private HotelController hotels;

    @Inject
    private FoodConceptController foodConcepts;

    @Inject
    private CacheService cacheService;

    @Inject
    private RoomPoolController roomPools;

    @Inject
    private RoomFeatureController roomFeatures;

    @Inject
    private AddressService addressService;

    @Inject
    private ApprovalController approvals;


    private LazyDataModel<Hotel> hotelsLazy;

    private HotelSearchCriteria criteria;

    @PostConstruct
    private void init(){
        FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put("hotelService",this);
        this.criteria=new HotelSearchCriteria();
        hotelsLazy=new LazyDataModel<Hotel>() {
            public List<Hotel> load(int first, int pageSize, String sortField,
                                    SortOrder sortOrder, java.util.Map<String, Object> filters) {
                List<Hotel> result=null;

                criteria.setFirstResult(first);
                criteria.setPageSize(pageSize);
                criteria.setSortField(sortField);
                criteria.setAscending(SortOrder.ASCENDING.equals(sortOrder));

                result=hotels.searchHotels(criteria);
                this.setRowCount(criteria.getResultSize());

                return result;
            }
            public Hotel getRowData(String rowKey) {
                return hotels.findByLongId(Long.parseLong(rowKey));
            }

            public Object getRowKey(Hotel hotel) {
                return hotel.getId();
            }

        };
    }

    public void resetSearchCriteria(){
        criteria=new HotelSearchCriteria();
    }

    public HotelSearchCriteria getSearchCriteria(){
        return criteria;
    }


    public Hotel findById(Long id){
        return this.hotels.findOptionalById(id).orElseThrow(()->new HotelNotFoundException(id));
    }

    public LazyDataModel<Hotel> getHotelsLazy(){
        return this.hotelsLazy;
    }

    public List<Hotel> getHotels(){
        return this.hotels.findAll();
    }

    private EditHotelBean cleanHotelBean(EditHotelBean hotelBean){
        hotelBean.setId(null);
        hotelBean.setName("");
        hotelBean.setEmail("");
        hotelBean.setPhone("");
        hotelBean.setFax("");
        hotelBean.setDescr("");
        hotelBean.setLongDescr("");
        hotelBean.setVk("");
        hotelBean.setFb("");
        hotelBean.setSite("");
        hotelBean.setStars(5);
        hotelBean.setPlace("");

        hotelBean.setPicture(null);

        hotelBean.getFoodConcepts().clear();
        hotelBean.getRoomPools().clear();
        hotelBean.getRoomFeatures().clear();
        addressService.cleanAddressBean(hotelBean.getAddress());
        cleanPaymentPolicyBean(hotelBean.getPaymentPolicy());
        return hotelBean;
    }

    public EditHotelBean initHotelBean(EditHotelBean hotelBean, Long hotelId){
        Hotel hotel =null;
        if (hotelId!=null){
            hotel=hotels.findOptionalById(hotelId).orElse(null);
        }
        return initHotelBean(hotelBean,hotel);
    }

    public EditHotelBean initHotelBean(EditHotelBean hotelBean, Hotel hotel){
        if (hotel!=null) {
            hotelBean.setId(hotel.getId());
            hotelBean.setName(hotel.getName());
            hotelBean.setEmail(hotel.getEmail());
            hotelBean.setPhone(hotel.getPhone());
            hotelBean.setFax(hotel.getFax());
            hotelBean.setDescr(hotel.getDescr());
            hotelBean.setLongDescr(hotel.getLongDescr());
            hotelBean.setVk(hotel.getVk());
            hotelBean.setFb(hotel.getFb());
            hotelBean.setSite(hotel.getSite());
            hotelBean.setStars(hotel.getStars());
            hotelBean.setPlace(hotel.getPlace());

            addressService.initAddressBean(hotelBean.getAddress(),hotel.getAddress());
            initPaymentPolicyBean(hotelBean.getPaymentPolicy(),hotel.getPaymentPolicy());

            initHotelPicture(hotelBean,hotel.getPicture());

            List<EditFoodConceptBean> list=hotelBean.getFoodConcepts();
            list.clear();
            hotel.getFoodConcepts().stream().forEach(fc->list.add(initFoodConceptBean(new EditFoodConceptBean(Long.valueOf(list.size())),fc)));

            List<EditRoomFeatureBean> rflist=hotelBean.getRoomFeatures();
            rflist.clear();
            hotel.getRoomFeatures().stream().forEach(rf->rflist.add(initRoomFeatureBean(new EditRoomFeatureBean(Long.valueOf(rflist.size())),rf)));

            List<EditRoomPoolBean> rplist=hotelBean.getRoomPools();
            rplist.clear();
            hotel.getRoomPools().stream().forEach(rp->rplist.add(initRoomPoolBean(new EditRoomPoolBean(Long.valueOf(rplist.size())),rp)));


            return hotelBean;
        }else{
            return cleanHotelBean(hotelBean);
        }
    }

    public void initHotelPicture(EditHotelBean hotel,byte[] picture){
        if (picture!=null){
            byte[] image= Arrays.copyOf(picture, picture.length);
            hotel.setPicture(image);
        }
    }

    public EditHotelBean initAwaitingHotelBean(EditHotelBean hotelBean, Hotel hotel){
        initHotelBean(hotelBean,hotel);
        hotelBean.setAwaiting(true);

        return hotelBean;
    }

    public ApprovalBean cleanApprovalBean(ApprovalBean approvalBean, EditHotelBean hotelBean){
        approvalBean.setId(null);
        approvalBean.setApprovedState(0);
        approvalBean.setApprovalDate(Calendar.getInstance().getTime());
        approvalBean.setHotel(hotelBean);
        approvalBean.setAwaitingHotel(new EditHotelBean());

        return approvalBean;
    }


    public ApprovalBean initApprovalBean(ApprovalBean approvalBean, Approval approval, EditHotelBean hotelBean){
        if (approval!=null){
            approvalBean.setId(approval.getId());
            approvalBean.setApprovalDate(approval.getApprovalDate());
            approvalBean.setApprovedState(approval.getApprovedState());

            approvalBean.setHotel(hotelBean);
            approvalBean.setAwaitingHotel(initAwaitingHotelBean(approvalBean.getAwaitingHotel(), approval.getAwaitingHotel()));

            return approvalBean;
        }else{
            return cleanApprovalBean(approvalBean, hotelBean);
        }
    }

    public void requestApproval(ApprovalBean approvalBean){
        approvalBean.setApprovedState(1);
        approvals.updateApprovalState(approvalBean);

    }

    public EditPaymentPolicyBean cleanPaymentPolicyBean(EditPaymentPolicyBean ppBean){
        ppBean.setId(null);
        ppBean.setPrePayPercent(0.);
        return ppBean;
    }

    public EditPaymentPolicyBean initPaymentPolicyBean(EditPaymentPolicyBean ppBean, HotelPaymentPolicy pp){
        if (pp!=null){
            ppBean.setId(pp.getId());
            ppBean.setPrePayPercent(pp.getPrePayPercent());
            return ppBean;
        }else{
            return cleanPaymentPolicyBean(ppBean);
        }
    }

    public EditFoodConceptBean initFoodConceptBean(EditFoodConceptBean conceptBean, Long conceptId){
        rnk.bb.domain.hotel.resource.FoodConcept concept=null;
        if (conceptId!=null){
            concept=foodConcepts.findOptionalById(conceptId).orElse(null);
        }
        EditFoodConceptBean result=conceptBean;
        if (conceptBean==null)
        {
            result=new EditFoodConceptBean();
        }
        return initFoodConceptBean(result,concept);
    }

    private EditFoodConceptBean cleanFoodConceptBean(EditFoodConceptBean fcBean){
        fcBean.setId(null);
        fcBean.setHotel(null);
        fcBean.setBasePrice(null);
        fcBean.setDescription("");
        fcBean.setName("");

        return fcBean;
    }

    public EditFoodConceptBean initFoodConceptBean(EditFoodConceptBean fcBean, FoodConcept fc){
        if (fc!=null) {
            fcBean.setId(fc.getId());
            fcBean.setHotel(fc.getHotel());
            fcBean.setName(fc.getName());
            fcBean.setBasePrice(fc.getBasePrice());
            fcBean.setDescription(fc.getDescription());
            return fcBean;
        }else{
            return cleanFoodConceptBean(fcBean);
        }
    }

    public EditFoodConceptBean initFoodConceptBean(EditFoodConceptBean fcBean, EditFoodConceptBean anotherBean){
        if ((fcBean!=null)&&(anotherBean!=null)) {
            FoodConcept foodConcept=foodConcepts.findByLongId(anotherBean.getId());

            fcBean.setHotel(foodConcept.getHotel());
            fcBean.setId(foodConcept.getId());
            fcBean.setName(foodConcept.getName());
            fcBean.setBasePrice(foodConcept.getBasePrice());
            fcBean.setDescription(foodConcept.getDescription());
            return fcBean;
        }else{
            return cleanFoodConceptBean(fcBean);
        }
    }

    public void removeFoodConceptBean(EditHotelBean hotelBean, EditFoodConceptBean fcBean){
        hotelBean.getFoodConcepts().remove(fcBean);
    }

    public void saveFoodConceptBean(EditHotelBean hotelBean, EditFoodConceptBean fcBean){
        if (fcBean.getFakeId()==null){
            fcBean.setFakeId(Long.valueOf(hotelBean.getFoodConcepts().size()));
            hotelBean.getFoodConcepts().add(fcBean);
        }
    }


    private EditRoomPoolBean cleanRoomPoolBean(EditRoomPoolBean rpBean){
        rpBean.setId(null);
        rpBean.setHotel(null);
        rpBean.setName("");
        rpBean.setBasePrice(null);
        rpBean.setRoomType(null);
        return rpBean;
    }

    public EditRoomPoolBean initRoomPoolBean(EditRoomPoolBean rpBean, RoomPool rp){
        if (rp!=null) {
            rpBean.setId(rp.getId());
            rpBean.setHotel(rp.getHotel());
            rpBean.setName(rp.getName());
            rpBean.setBasePrice(rp.getBasePrice());

            rpBean.setRoomType(cacheService.getRoomTypeById(rp.getRoomType().getName()));
            return rpBean;
        }else{
            return cleanRoomPoolBean(rpBean);
        }
    }

    public EditRoomPoolBean initRoomPoolBean(EditRoomPoolBean rpBean, EditRoomPoolBean anotherBean){
        if ((rpBean!=null)&&(anotherBean!=null)) {
            RoomPool roomPool=roomPools.findByLongId(anotherBean.getId());

            rpBean.setId(roomPool.getId());
            rpBean.setHotel(roomPool.getHotel());
            rpBean.setName(roomPool.getName());
            rpBean.setBasePrice(roomPool.getBasePrice());

            rpBean.setRoomType(anotherBean.getRoomType());

            return rpBean;
        }else{
            return cleanRoomPoolBean(rpBean);
        }
    }

    public void removeRoomPoolBean(EditHotelBean hotelBean, EditRoomPoolBean rpBean){
        hotelBean.getRoomPools().remove(rpBean);
    }

    public void saveRoomPoolBean(EditHotelBean hotelBean, EditRoomPoolBean rpBean){
        if (rpBean.getFakeId()==null){
            rpBean.setFakeId(Long.valueOf(hotelBean.getRoomPools().size()));
            hotelBean.getRoomPools().add(rpBean);
        }
    }


    private EditRoomFeatureBean cleanRoomFeatureBean(EditRoomFeatureBean rfBean){
        rfBean.setId(null);
        rfBean.setHotel(null);
        rfBean.setPrice(null);
        rfBean.setDescription("");
        rfBean.setName("");

        return rfBean;
    }

    public EditRoomFeatureBean initRoomFeatureBean(EditRoomFeatureBean rfBean, RoomFeature rf){
        if (rf!=null) {
            rfBean.setId(rf.getId());
            rfBean.setHotel(rf.getHotel());
            rfBean.setName(rf.getName());
            rfBean.setPrice(rf.getPrice());
            rfBean.setDescription(rf.getDescription());
            return rfBean;
        }else{
            return cleanRoomFeatureBean(rfBean);
        }
    }

    public EditRoomFeatureBean initRoomFeatureBean(EditRoomFeatureBean rfBean, EditRoomFeatureBean anotherBean){
        if ((rfBean!=null)&&(anotherBean!=null)) {
            RoomFeature rf=roomFeatures.findByLongId(anotherBean.getId());

            rfBean.setHotel(rf.getHotel());
            rfBean.setId(rf.getId());
            rfBean.setName(rf.getName());
            rfBean.setPrice(rf.getPrice());
            rfBean.setDescription(rf.getDescription());
            return rfBean;
        }else{
            return cleanRoomFeatureBean(rfBean);
        }
    }

    public void removeRoomFeatureBean(EditHotelBean hotelBean, EditRoomFeatureBean rfBean){
        hotelBean.getRoomFeatures().remove(rfBean);
    }

    public void saveRoomFeatureBean(EditHotelBean hotelBean, EditRoomFeatureBean rfBean){
        if (rfBean.getFakeId()==null){
            rfBean.setFakeId(Long.valueOf(hotelBean.getRoomFeatures().size()));
            hotelBean.getRoomFeatures().add(rfBean);
        }
    }


}
