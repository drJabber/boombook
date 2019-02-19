package rnk.bb.rest.hotel.resource;

import rnk.bb.domain.hotel.resource.FoodConcept;
import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.domain.hotel.staff.Staff;
import rnk.bb.rest.util.ServerUtils;
import rnk.bb.rest.util.StringUtils;
import rnk.bb.rest.blank.CustomController;
import rnk.bb.rest.hotel.staff.StaffController;
import rnk.bb.rest.util.AddressController;
import rnk.bb.rest.util.HotelSearchCriteria;
import rnk.bb.views.bean.hotel.EditHotelBean;
import rnk.bb.views.bean.registration.StaffUserBean;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@Startup
@DependsOn({"StartupController"})
@Path("v1")
public class HotelController  extends CustomController<Hotel, Long> {
    private static Logger log=Logger.getLogger(HotelController.class.getName());
    @PUT
    @Path("hotel/resource/hotel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(JsonObject info) {
        return saveInternal(info);
    }

    @POST
    @Path("hotel/resource/hotel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(JsonObject info) {
        return saveInternal(info);
    }

    @GET
    @Path("hotel/resource/hotel/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") Long foodConceptId) {
        return readInternal(foodConceptId);
    }

    @DELETE
    @Path("hotel/resource/hotel/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response delete(@PathParam("id") Long foodConceptId) {
        return deleteInternal(foodConceptId);
    }


    @PostConstruct
    public void init(){
        Map<String, Object> filter=new HashMap<>();
        filter.put("published",true);
        super.init(filter);
    }


    @Inject
    StaffController staff;

    @Inject
    AddressController addresses;

    @Inject
    FoodConceptController concepts;

    @Inject
    HotelPaymentPolicyController policies;

    @Inject
    RoomFeatureController roomFeatures;

    @Inject
    RoomPoolController roomPools;

    @Inject
    StringUtils stringUtils;

    @Inject
    ServerUtils serverUtils;

    public List<FoodConcept> getFoodConceptList(Long hotelId){
        CriteriaBuilder cb = this.entityManager().getCriteriaBuilder();
        CriteriaQuery<FoodConcept> q = cb.createQuery(FoodConcept.class);
        Root<FoodConcept> root = q.from(FoodConcept.class);
        Join<FoodConcept,Hotel> hotelJoin=root.join("hotelId", JoinType.INNER);
        List<Predicate> predicates=new ArrayList<>();
        predicates.add(cb.equal(hotelJoin.get("hotelId"),hotelId));
        q.select(root).where(predicates.toArray(new Predicate[0]));

        return entityManager().createQuery(q).getResultList();
    }

    private void updateHotelStaff(Hotel hotel, EditHotelBean hotelBean){
        StaffUserBean managerBean=hotelBean.getManager();
        if ((managerBean!=null)&&(managerBean.getStaffId()!=null)){
            Staff manager=staff.findById(managerBean.getStaffId());
            hotel.setManager(manager);

            List<Staff> staffList=new ArrayList<>();
            staffList.add(manager);
            hotel.setStaffList(staffList);
        }

    }
    public Hotel createNewHotel(EditHotelBean hotelBean){
        Hotel hotel=new Hotel();

        return updateHotel(hotel,hotelBean);
    }


    public Hotel updateHotel(Hotel hotel, EditHotelBean hotelBean){
        hotel.setName(hotelBean.getName());
        hotel.setPhone(hotelBean.getPhone());
        hotel.setEmail(hotelBean.getEmail());
        hotel.setSite(hotelBean.getSite());
        hotel.setPublished(hotelBean.getPublished());
        hotel.setFax(hotelBean.getFax());
        hotel.setDescr(hotelBean.getDescr());
        hotel.setFb(hotelBean.getFb());
        hotel.setVk(hotelBean.getVk());
        hotel.setLongDescr(hotelBean.getLongDescr());
        hotel.setPlace(hotelBean.getPlace());
        hotel.setStars(hotelBean.getStars());


        updateHotelStaff(hotel, hotelBean);

        hotel.setAddress(addresses.createOrUpdateAddress(hotelBean.getAddress()));

        hotel.setApproval(null);

        hotel.setFoodConcepts(concepts.createOrUpdateFoodConcepts(hotelBean.getFoodConcepts(),hotel));

        hotel.setPaymentPolicy(policies.createOrUpdatePaymentPolicy(hotelBean.getPaymentPolicy()));

        hotel.setRoomFeatures(roomFeatures.createOrUpdateRoomFeatures(hotelBean.getRoomFeatures(),hotel));

        hotel.setRoomPools(roomPools.createOrUpdateRoomPools(hotelBean.getRoomPools(), hotel));

        ///todo - fill empty fields
        hotel.setScheduleItems(null);


        return hotel;
    }


    public List<Hotel> searchHotels(HotelSearchCriteria criteria){
        log.log(Level.INFO,"search hotel...");

        String query="from Hotel h left join Address a  on a=h.address left join Country c on c=a.country where h.published=true ";
        String countQuery="select count(h) ";

        List<Hotel> hotels=new ArrayList<>();

        try{
            StringBuilder sb=new StringBuilder(query);

            //Filters
            Map params=new HashMap();

            if (stringUtils.isNotBlank(criteria.getCountry())){
                sb=sb.append("and c.nameRu like :country ");
                params.put("country","%"+criteria.getCountry()+"%");
            }
            if (stringUtils.isNotBlank(criteria.getTown())){
                sb=sb.append("and a.settlementPart like :town ");
                params.put("town","%"+criteria.getTown()+"%");
            }
            if (stringUtils.isNotBlank(criteria.getHotelName())){
                sb=sb.append("and h.name like :hotelName ");
                params.put("hotelName","%"+criteria.getHotelName()+"%");
            }
            try
            {
                if (stringUtils.isNotBlank(criteria.getStars())){
                    sb=sb.append("and h.stars = :stars ");
                    params.put("stars",Integer.parseInt(criteria.getStars()));
                }
            }catch(Exception ex){
                log.log(Level.INFO, "no stars");

            }

            countQuery+=sb.toString();
            sb=sb.append("order by ");

            if (stringUtils.isNotBlank(criteria.getSortField())){
                sb=sb.append("lower(h.").append(criteria.getSortField()).append(")").append(criteria.getAscending()?" asc, ":" desc,");
            }

            sb.append("lower(c.nameRu), lower(h.name), lower(a.settlementPart) ");

            Query q=entityManager().createQuery(countQuery);
            params.keySet().stream().forEach(k->q.setParameter(k.toString(),params.get(k)));
            int resultSize=((Long)q.getSingleResult()).intValue();
            criteria.setResultSize(resultSize);

            if (resultSize!=0){
                int firstResult=criteria.getFirstResult();
                int pageSize=criteria.getPageSize();

                firstResult=serverUtils.computeFirstResult(firstResult,pageSize,resultSize);

                Query qc=entityManager().createQuery(sb.toString());
                params.keySet().stream().forEach(k->qc.setParameter(k.toString(),params.get(k)));
                qc.setFirstResult(firstResult);
                qc.setMaxResults(pageSize);

                hotels=qc.getResultList();
            }
        }catch(Exception ex){
            log.log(Level.SEVERE, ex.getMessage());
        }

        return hotels;
    }


    public Integer getHotelsCount(HotelSearchCriteria criteria){
        Integer count=0;
        String query="select count(h) from Hotel h left join Address a on a=h.address left join Country c on c=a.country where h.published=true ";
        try{
            StringBuilder sb=new StringBuilder(query);

            //Filters
            Map params=new HashMap();

            if (stringUtils.isNotBlank(criteria.getCountry())){
                sb=sb.append("and c.nameRu like :country ");
                params.put("country","%"+criteria.getCountry()+"%");
            }
            if (stringUtils.isNotBlank(criteria.getTown())){
                sb=sb.append("and a.settlementPart like :town ");
                params.put("town","%"+criteria.getTown()+"%");
            }
            if (stringUtils.isNotBlank(criteria.getHotelName())){
                sb=sb.append("and h.name like :hotelName ");
                params.put("hotelName","%"+criteria.getHotelName()+"%");
            }
            try
            {
                if (stringUtils.isNotBlank(criteria.getStars())){
                    sb=sb.append("and h.stars = :stars ");
                    params.put("stars",Integer.parseInt(criteria.getStars()));
                }
            }catch(Exception ex){
                log.log(Level.INFO, "no stars");

            }

            query+=sb.toString();

            Query q=entityManager().createQuery(query);
            params.keySet().stream().forEach(k->q.setParameter(k.toString(),params.get(k)));
            count=((Long)q.getSingleResult()).intValue();
            criteria.setResultSize(count);

        }catch(Exception ex){
            log.log(Level.SEVERE, ex.getMessage());
        }

        return count;
    }

}
