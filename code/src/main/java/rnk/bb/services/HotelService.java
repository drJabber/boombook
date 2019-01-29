package rnk.bb.services;

import org.primefaces.model.LazyDataModel;
import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.rest.hotel.resource.HotelController;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class HotelService implements Serializable {

    @Inject
    HotelController hotels;

    public Hotel findById(Long id){
        return this.hotels.findOptionalById(id).orElseThrow(()->new HotelNotFoundException(id));
    }

    public LazyDataModel<Hotel> getController(){
        return this.hotels;
    }

    public List<T> findAll() {

        CriteriaBuilder cb = this.entityManager().getCriteriaBuilder();

        CriteriaQuery<T> q = cb.createQuery(entityClass());
        Root<T> c = q.from(entityClass());

        return entityManager().createQuery(q).getResultList();
    }

    public List<FoodConcept> getFoodConceptList(Long hotelId){
        CriteriaBuilder cb = this.entityManager().getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(entityClass());
        Root<T> c = q.from(entityClass());
        
        return this.
    }

}
