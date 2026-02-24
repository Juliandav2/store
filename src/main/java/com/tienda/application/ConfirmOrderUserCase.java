package com.tienda.application;
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

    public ConfirmOrderUserCase (OrderRepository repository) {
        this.repository = repository;
    }

    public void execute (String orderId) {

        Order order = repository.findById(orderId).orElseThrow(() -> new NoSuchElementException(orderId));
        order.confirm();
    }
}
