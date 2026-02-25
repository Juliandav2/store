package com.tienda.repository;
import com.tienda.model.Order;
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
     */

    void save (Order order);

    /**
     * Retrieves an order by its identifier.
     *
     * @param id the order identifier
     * @return an Optional containing the order if found, or empty if not found
     */

    Optional<Order> findById (String id);

}
