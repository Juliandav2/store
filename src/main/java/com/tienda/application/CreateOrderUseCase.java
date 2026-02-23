package com.tienda.application;
import com.tienda.model.Customer;
import com.tienda.model.Order;
import com.tienda.repository.OrderRepository;
import java.util.UUID;

/**
 * Application use case responsible for creating new orders.
 *
 * <p>This class orchestrates the creation process and delegates
 * business rule enforcement to the Order entity.</p>
 */

public class CreateOrderUseCase {

    private final OrderRepository repository;

    public CreateOrderUseCase (OrderRepository repository) {
        this.repository = repository;
    }

    /**
     * Creates and persists a new order for the given customer.
     *
     * @param customer the customer placing the order
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
}
