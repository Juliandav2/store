package com.tienda.application;
import com.tienda.exception.OrderNotFoundException;
import com.tienda.model.Order;
import com.tienda.repository.OrderRepository;

/**
 * Application use case responsible for processing
 * the payment of an Order.
 *
 * <p>
 * This use case ensures the Order is retrieved,
 * delegates payment state transition to the domain,
 * and persists the updated aggregate.
 * </p>
 *
 * <p>
 * Validation of valid state transitions
 * is handled within the Order entity.
 * </p>
 *
 * <p>
 * Layer: Application
 * Responsibility: Orchestrating payment workflow
 * </p>
 */

public class CancelOrderUserCase {

    private final OrderRepository repository;

    public CancelOrderUserCase (OrderRepository repository) {
        this.repository = repository;
    }

    public void execute (String orderId) {

        Order order = repository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
        order.cancel();
    }
}
