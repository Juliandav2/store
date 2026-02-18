package com.tienda.repository;
import com.tienda.model.Order;
import java.util.Optional;

public interface OrderRepository {

    void save (Order order);
    Optional<Order> findById (String id);

}
