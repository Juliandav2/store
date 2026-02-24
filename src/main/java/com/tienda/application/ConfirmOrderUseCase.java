package com.tienda.application;
import com.tienda.exception.OrderNotFoundException;
import com.tienda.model.Order;
import com.tienda.repository.OrderRepository;
import java.util.NoSuchElementException;

/**
 * Application use case responsible for confirming an Order.
 *
 * <p>
 * This use case loads the Order,
 * delegates confirmation logic to the domain,
 * and persists state changes.
 * </p>
 *
 * <p>
 * Domain invariants such as preventing confirmation
 * of empty orders are enforced inside the Order entity.
 * </p>
 *
 * <p>
 * Layer: Application
 * Responsibility: State transition orchestration
 * </p>
 */

public class ConfirmOrderUserCase {

    private final OrderRepository repository;

    /**
     * Creates a new instance of the use case.
     *
     * @param repository repository used to retrieve and persist orders
     */

    public ConfirmOrderUserCase (OrderRepository repository) {
        this.repository = repository;
    }

    /**
     * Confirms an existing order.
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
        order.confirm();
        repository.save(order);
    }
}
