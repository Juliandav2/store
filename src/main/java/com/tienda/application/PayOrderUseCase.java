package com.tienda.application;
import com.tienda.exception.OrderNotFoundException;
import com.tienda.model.Order;
import com.tienda.repository.OrderRepository;

/**
 * Application use case responsible for processing
 * the payment of an Order.
 *
 * <p>
 * This use case retrieves the Order,
 * delegates payment state transition to the domain,
 * and persists the updated aggregate.
 * </p>
 *
 * <p>
 * Validation of valid payment transitions
 * is handled within the Order entity.
 * </p>
 *
 * Layer: Application
 * Responsibility: Orchestrating payment workflow
 */

public class PayOrderUseCase {

    private final OrderRepository repository;

    /**
     * Creates a new instance of the use case.
     *
     * @param repository repository used to retrieve and persist orders
     */

    public PayOrderUseCase(OrderRepository repository) {
        this.repository = repository;
    }

    /**
     * Processes payment for an existing order.
     *
     * @param orderId identifier of the order
     * @throws IllegalArgumentException if orderId is null or blank
     * @throws OrderNotFoundException if the order does not exist
     */

    public void execute (String orderId) {

        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("OrderId cannot be null or blank");
        }

        Order order = repository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
        order.pay();
        repository.save(order);
    }
}
