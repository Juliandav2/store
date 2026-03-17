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

public class AddProductOrderUseCase {

    private final OrderRepository repository;

    /**
     * Creates a new instance of the use case.
     *
     * @param repository repository used to retrieve and persist orders
     */

    public AddProductOrderUseCase(OrderRepository repository) {
        this.repository = repository;

    }

    /**
     * Adds a product to an existing order.
     *
     * @param orderId  identifier of the order
     * @param product  product to be added
     * @param quantity quantity of the product
     * @throws IllegalArgumentException if orderId or product is null,
     *                                  or quantity is not greater than zero
     * @throws OrderNotFoundException if the order does not exist
     */

    public void execute (String orderId, Product product, int quantity) {

        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("OrderId cannot be null or blank");
        }

        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        Order order = repository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
        ItemOrder item = new ItemOrder(product, quantity, product.getPrice());
        order.addItem(item);
        repository.save(order);

    }

}
