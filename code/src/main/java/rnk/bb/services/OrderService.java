package rnk.bb.services;

import rnk.bb.domain.book.Order;
import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.rest.book.OrderController;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;

@ApplicationScoped
public class OrderService implements Serializable {
    @Inject
    OrderController orders;

    public Order findById(Long id){
        return this.orders.findOptionalById(id).orElseThrow(()->new HotelNotFoundException(id));
    }

}
