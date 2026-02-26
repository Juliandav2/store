package com.tienda.application;

import com.tienda.dto.CreateOrderRequest;
import com.tienda.model.*;
import com.tienda.repository.OrderRepository;
import java.util.UUID;

/**
 * Application use case responsible for creating a new Order.
 *
 * <p>
 * This use case builds the customer from the request data,
 * creates the Order aggregate and persists it through the repository.
 * </p>
 *
 * <p>
 * Supported customer types: REGULAR, PREMIUM.
 * </p>
 *
 * <p>Layer: Application — Responsibility: Orchestration</p>
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
     * Creates and persists a new Order from the given request.
     *
     * @param request data required to create the order
     * @return the newly created Order
     * @throws IllegalArgumentException if request is null or customerType is invalid
     */

    public Order execute (CreateOrderRequest request) {

        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        Customer customer = buildCustomer(request);
        Order order = new Order(generateId(), customer);
        repository.save(order);
        return order;
    }

    /**
     * Builds the appropriate Customer subtype based on the request.
     *
     * @param request the creation request containing customer data
     * @return a RegularCustomer or PremiumCustomer instance
     * @throws IllegalArgumentException if customerType is not recognized
     */

    private Customer buildCustomer (CreateOrderRequest request) {
        return switch (request.getCustomerType().toUpperCase()) {
            case "REGULAR" -> new RegularCustomer(request.getCustomerId(), request.getCustomerName());
            case "PREMIUM" -> new PremiumCustomer(request.getCustomerId(), request.getCustomerName());
            default -> throw new IllegalArgumentException("Unknow customer type: " + request.getCustomerType() + ". Expected REGULAR or PREMIUM");
        };
    }

    /**
     * Generates a unique identifier for the order.
     *
     * @return unique order identifier
     */

    private String generateId () {
        return UUID.randomUUID().toString();
    }
}
