package com.tienda.application;
import com.tienda.model.Customer;
import com.tienda.model.Order;
import com.tienda.repository.OrderRepository;
import java.util.UUID;

/**
 * Application use case responsible for creating a new Order.
 *
 * <p>
 * This use case orchestrates the creation of the Order aggregate
 * and persists it through the OrderRepository.
 * </p>
 *
 * <p>
 * It does not contain business rules related to order lifecycle.
 * Those rules belong to the Order domain entity.
 * </p>
 *
 * <p>
 * Layer: Application
 * Responsibility: Orchestration
 * </p>
 */

public class CreateOrderUseCase {

    private final OrderRepository repository;

    /**
     * Creates a new instance of the use case.
     *
     * @param repository repository used to persist orders
     */

    public CreateOrderUseCase (OrderRepository repository) {
        this.repository = repository;
    }

    /**
     * Creates and persists a new Order for the given customer.
     *
     * @param customer customer who owns the order
     * @return the newly created Order
     * @throws IllegalArgumentException if customer is null
     */

    public Order execute (Customer customer) {

        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        Order order = new Order(UUID.randomUUID().toString(), customer);
        repository.save(order);

        return order;
    }

    /**
     * Generates a unique identifier for the order.
     *
     * <p>
     * Extracted into a separate method to isolate ID generation
     * responsibility and improve testability.
     * </p>
     *
     * @return unique order identifier
     */

    private String generateId () {
        return UUID.randomUUID().toString();
    }
}
