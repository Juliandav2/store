package com.tienda.service;
import com.tienda.application.*;
import com.tienda.model.*;
import com.tienda.repository.OrderRepository;

/**
 * Facade service that coordinates order-related use cases.
 *
 * <p>
 * This class provides a simplified entry point for external layers
 * (e.g., controllers) while delegating business logic
 * to application use cases.
 * </p>
 *
 * Layer: Application Facade
 * Responsibility: Delegation and coordination
 */

public class OrderService {

    private final CreateOrderUseCase createOrderUseCase;
    private final AddProductOrderUseCase addProductOrderUseCase;
    private final ConfirmOrderUseCase confirmOrderUseCase;
    private final PayOrderUseCase payOrderUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;

    /**
     * Creates a new instance of the service
     * initializing all required use cases.
     *
     * @param repository repository used by the use cases
     */

    public OrderService (OrderRepository repository) {

        this.createOrderUseCase = new CreateOrderUseCase(repository);
        this.addProductOrderUseCase = new AddProductOrderUseCase(repository);
        this.confirmOrderUseCase = new ConfirmOrderUseCase(repository);
        this.payOrderUseCase = new PayOrderUseCase(repository);
        this.cancelOrderUseCase = new CancelOrderUseCase(repository);

    }

    /**
     * Creates a new order for the given customer.
     *
     * @param customer customer who owns the order
     * @return created order
     */

    public Order createOrder (Customer customer) {
        return createOrderUseCase.execute(customer);
    }

    /**
     * Adds a product to an existing order.
     *
     * @param orderId  identifier of the order
     * @param product  product to add
     * @param quantity quantity of the product
     */

    public void addProduct (String orderId, Product product, int quantity) {
        addProductOrderUseCase.execute(orderId, product,quantity);
    }

    /**
     * Confirms an existing order.
     *
     * @param orderId identifier of the order
     */

    public void confirm (String orderId) {
        confirmOrderUseCase.execute(orderId);
    }

    /**
     * Processes payment for an order.
     *
     * @param orderId identifier of the order
     */

    public void pay (String orderId) {
        payOrderUseCase.execute(orderId);
    }

    /**
     * Processes payment for an order.
     *
     * @param orderId identifier of the order
     */

    public void cancel (String orderId) {
        cancelOrderUseCase.execute(orderId);
    }
}

