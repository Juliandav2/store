package com.tienda.repository;

import com.tienda.model.Order;

import java.util.*;

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
     * @throws NullPointerException if order is null
     */

    @Override
    public void save (Order order) {
        Objects.requireNonNull(order, "Order cannot be null");
        storage.put(order.getId(), order);
    }

    /**
     * Retrieves an order from memory by its identifier.
     *
     * @param id the order identifier
     * @return an Optional containing the order if found, or empty if not found
     * @throws NullPointerException if id is null
     */

    @Override
    public Optional<Order> findById (String id) {
        Objects.requireNonNull(id, "Id cannot be null");
        return Optional.ofNullable(storage.get(id));
    }

    /**
     * Clears all stored orders from memory.
     *
     * <p>
     * Intended for use in tests to ensure a clean state between executions.
     * </p>
     */

    @Override
    public void deleteAll () {
        storage.clear();
    }

    @Override
    public org.springframework.data.domain.Page<Order> findAll(
            org.springframework.data.domain.Pageable pageable) {
        return new org.springframework.data.domain.PageImpl<>(
                new java.util.ArrayList<>(storage.values()));
    }
}
