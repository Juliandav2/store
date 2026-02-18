package com.tienda.repository;
import com.tienda.model.Order;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class InMemoryRepository implements OrderRepository {

    private final Map<String, Order> storage = new HashMap<>();

    @Override
    public void save (Order order) {
        storage.put(order.getId(), order);
    }

    @Override
    public Optional<Order> findById (String id) {
        return Optional.ofNullable(storage.get(id));
    }
}
