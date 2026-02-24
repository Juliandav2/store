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

    public CreateOrderUseCase (OrderRepository repository) {
        this.repository = repository;
    }

    public Order execute (Customer customer) {

        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        Order order = new Order(UUID.randomUUID().toString(), customer);
        repository.save(order);

        return order;
    }
}
