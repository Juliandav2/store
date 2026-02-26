package com.tienda.service;

import com.tienda.application.*;
import com.tienda.dto.CreateOrderRequest;
import com.tienda.model.Order;
import com.tienda.model.Product;
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
 * <p>
 * Each method maps directly to a single use case,
 * keeping this class free of business logic.
 * </p>
 *
 * <p>Layer: Application Facade — Responsibility: Delegation and coordination</p>
 */

public class OrderService {

    private final CreateOrderUseCase createOrderUseCase;
    private final AddProductOrderUseCase addProductOrderUseCase;
    private final ConfirmOrderUseCase confirmOrderUseCase;
    private final PayOrderUseCase payOrderUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;
    private final RefundOrderUseCase refundOrderUseCase;

    /**
     * Creates a new instance of the service with all required use cases.
     *
     * <p>
     * Each use case is injected independently to allow easy replacement
     * and testing. This constructor is ready for Spring dependency injection.
     * </p>
     *
     * @param repository repository shared by all use cases
     */

    public OrderService (OrderRepository repository) {

        this.createOrderUseCase = new CreateOrderUseCase(repository);
        this.addProductOrderUseCase = new AddProductOrderUseCase(repository);
        this.confirmOrderUseCase = new ConfirmOrderUseCase(repository);
        this.payOrderUseCase = new PayOrderUseCase(repository);
        this.cancelOrderUseCase = new CancelOrderUseCase(repository);
        this.refundOrderUseCase = new RefundOrderUseCase(repository);

    }

    /**
     * Creates a new order based on the given request.
     *
     * @param request data required to create the order
     * @return the newly created Order
     */

    public Order createOrder (CreateOrderRequest request) {
        return createOrderUseCase.execute(request);
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
     * Processes payment for an existing order.
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

    /**
     * Processes a refund for an existing order.
     *
     * @param orderId identifier of the order
     */

    public void refund (String orderId) {
        refundOrderUseCase.execute(orderId);
    }
}

