package com.tienda.repository;
import com.tienda.model.Order;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface that defines persistence operations for {@link Order}.
 *
 * <p>
 * This interface acts as a persistence port in the application architecture.
 * It abstracts the storage mechanism used to save and retrieve orders.
 * </p>
 *
 * <p>
 * Concrete implementations may use in-memory storage, databases,
 * or external systems without affecting the domain logic.
 * </p>
 */

public interface OrderRepository {

    /**
     * Persists the given order.
     *
     * @param order the order to be saved
     * @throws NullPointerException if order is null
     */

    void save (Order order);

    /**
     * Retrieves an order by its identifier.
     *
     * @param id the order identifier
     * @return an Optional containing the order if found, or empty if not found
     * @throws NullPointerException if id is null
     */

    Optional<Order> findById (String id);

    /**
     * Removes all orders from the repository.
     *
     * <p>
     * Intended for use in tests to ensure a clean state between executions.
     * </p>
     */

    void deleteAll ();

    org.springframework.data.domain.Page<Order> findAll (org.springframework.data.domain.Pageable pageable);

    org.springframework.data.domain.Page<Order> findAll (org.springframework.data.jpa.domain.Specification<Order> specification,
                                                         org.springframework.data.domain.Pageable pageable);

}
