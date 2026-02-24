package com.tienda.application;
import com.tienda.exception.OrderNotFoundException;
import com.tienda.model.*;
import com.tienda.repository.OrderRepository;

/**
 * Application use case responsible for adding
 * an item to an existing Order.
 *
 * <p>
 * This use case retrieves the Order from the repository,
 * delegates item addition to the domain entity,
 * and ensures persistence after modification.
 * </p>
 *
 * <p>
 * Business validation of order state is enforced
 * inside the Order aggregate.
 * </p>
 *
 * <p>
 * Layer: Application
 * Responsibility: Coordination between domain and repository
 * </p>
 */

public class AddProductOrderUserCase {

    private final OrderRepository repository;

    public AddProductOrderUserCase (OrderRepository repository) {
        this.repository = repository;

    }

    public void execute (String orderId, Product product, int quantity) {

        if (product == null) {
            throw new IllegalArgumentException("Product cannot be negative");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        Order order = repository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
        ItemOrder item = new ItemOrder(product, quantity, product.getPrice());
        order.addItem(item);
    }
}
