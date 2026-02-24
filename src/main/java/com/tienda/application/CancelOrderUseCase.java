package com.tienda.application;
import com.tienda.exception.OrderNotFoundException;
import com.tienda.model.Order;
import com.tienda.repository.OrderRepository;

/**
 * Application use case responsible for canceling an Order.
 *
 * <p>
 * This use case retrieves the Order,
 * delegates cancellation validation to the domain,
 * and persists the updated aggregate.
 * </p>
 *
 * <p>
 * Restrictions such as preventing cancellation
 * after payment or delivery are enforced
 * inside the Order entity.
 * </p>
 *
 * Layer: Application
 * Responsibility: Managing order cancellation workflow
 */

public class CancelOrderUseCase {

    private final OrderRepository repository;

    /**
     * Creates a new instance of the use case.
     *
     * @param repository repository used to retrieve and persist orders
     */

    public CancelOrderUseCase(OrderRepository repository) {
        this.repository = repository;
    }

    /**
     * Cancels an existing order.
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
        order.cancel();
        repository.save(order);
    }
}
