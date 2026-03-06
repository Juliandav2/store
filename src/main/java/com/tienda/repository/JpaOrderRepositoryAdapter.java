package com.tienda.repository;

import com.tienda.model.Order;

import java.util.List;
import java.util.Optional;

/**
 * Adapter that bridges {@link JpaOrderRepository} with {@link OrderRepository}.
 *
 * <p>
 * This class allows the application layer to depend on the
 * {@link OrderRepository} interface without knowing about JPA.
 * </p>
 *
 * <p>
 * Pattern: Adapter
 * Layer: Infrastructure
 * </p>
 */

public class JpaOrderRepositoryAdapter implements OrderRepository {

    private final JpaOrderRepository jpa;

    public JpaOrderRepositoryAdapter (JpaOrderRepository jpa) {
        this.jpa = jpa;
    }

    /**
     * Saves or updates an order in the database.
     *
     * @param order the order to persist
     * @throws NullPointerException if order is null
     */

    @Override
    public void save (Order order) {
        jpa.save(order);
    }

    /**
     * Finds an order by its identifier.
     *
     * @param id the order identifier
     * @return Optional containing the order if found
     * @throws NullPointerException if id is null
     */

    @Override
    public Optional <Order> findById (String id) {
        return jpa.findById(id);
    }

    /**
     * Removes all orders from the database.
     * Intended for use in tests only.
     */

    @Override
    public void deleteAll () {
        jpa.deleteAll();
    }

    @Override
    public org.springframework.data.domain.Page<Order> findAll(org.springframework.data.domain.Pageable pageable) {
        return jpa.findAll(pageable);
    }

}
