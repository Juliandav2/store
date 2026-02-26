package com.tienda.application;

import com.tienda.exception.OrderNotFoundException;
import com.tienda.model.Order;
import com.tienda.repository.OrderRepository;

/**
 * Application use case responsible for processing
 * a refund request for an Order.
 *
 * <p>
 * This use case retrieves the Order, delegates refund
 * validation to the domain, and persists the updated aggregate.
 * </p>
 *
 * <p>
 * Eligibility rules (PAID or SENT state) are enforced
 * inside the Order entity via {@link Order#refund()}.
 * </p>
 *
 * <p>Layer: Application — Responsibility: Orchestrating refund workflow</p>
 */

public class RefundOrderUseCase {

    private final OrderRepository repository;

    /**
     * Creates a new instance of the use case.
     *
     * @param repository repository used to retrieve and persist orders
     */

    public RefundOrderUseCase (OrderRepository repository) {
        this.repository = repository;
    }

    /**
     * Processes a refund for an existing order.
     *
     * @param orderId identifier of the order
     * @throws IllegalArgumentException if orderId is null or blank
     * @throws OrderNotFoundException   if the order does not exist
     */

    public void execute (String orderId) {

        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("Order ID cannot be null or blank");
        }

        Order order = repository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
        order.refund();
        repository.save(order);
    }
}
