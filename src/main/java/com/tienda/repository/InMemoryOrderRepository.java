package com.tienda.repository;
import com.tienda.model.Order;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * In-memory implementation of {@link OrderRepository}.
 *
 * <p>
 * This repository stores orders in a HashMap for testing
 * and development purposes.
 * </p>
 *
 * <p>
 * It is not intended for production use, as data will be lost
 * when the application stops.
 * </p>
 */

public class InMemoryOrderRepository implements OrderRepository {

    private final Map<String, Order> storage = new HashMap<>();

    /**
     * Saves or replaces the given order in memory.
     *
     * @param order the order to store
     */

    @Override
    public void save (Order order) {
        storage.put(order.getId(), order);
    }

    /**
     * Retrieves an order from memory by its identifier.
     *
     * @param id the order identifier
     * @return an Optional containing the order if found
     */

    @Override
    public Optional<Order> findById (String id) {
        return Optional.ofNullable(storage.get(id));
    }
}
