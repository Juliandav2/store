package com.tienda.service;
import com.tienda.application.*;
import com.tienda.model.*;
import com.tienda.repository.OrderRepository;

/**
 * Facade service that coordinates order-related use cases.
 *
 * <p>This class provides a simplified entry point for external layers
 * (e.g., controllers) while delegating business logic to application use cases.</p>
 */

public class OrderService {

    private final CreateOrderUseCase createOrderUseCase;
    private final AddProductOrderUseCase addProductOrderUserCase;
    private final ConfirmOrderUseCase confirmOrderUserCase;
    private final PayOrderUseCase payOrderUserCase;
    private final CancelOrderUseCase cancelOrderUserCase;

    public OrderService (OrderRepository repository) {

        this.createOrderUseCase = new CreateOrderUseCase(repository);
        this.addProductOrderUserCase = new AddProductOrderUseCase(repository);
        this.confirmOrderUserCase = new ConfirmOrderUseCase(repository);
        this.payOrderUserCase = new PayOrderUseCase(repository);
        this.cancelOrderUserCase = new CancelOrderUseCase(repository);

    }

    public Order createOrder (Customer customer) {
        return createOrderUseCase.execute(customer);
    }

    public void addProduct (String orderId, Product product, int quantity) {
        addProductOrderUserCase.execute(orderId, product,quantity);
    }

    public void confirm (String orderId) {
        confirmOrderUserCase.execute(orderId);
    }

    public void pay (String orderId) {
        payOrderUserCase.execute(orderId);
    }

    public void cancel (String orderId) {
        cancelOrderUserCase.execute(orderId);
    }
}

