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
    private final AddProductOrderUserCase addProductOrderUserCase;
    private final ConfirmOrderUserCase confirmOrderUserCase;
    private final PayOrderUserCase payOrderUserCase;
    private final CancelOrderUserCase cancelOrderUserCase;

    public OrderService (OrderRepository repository) {

        this.createOrderUseCase = new CreateOrderUseCase(repository);
        this.addProductOrderUserCase = new AddProductOrderUserCase(repository);
        this.confirmOrderUserCase = new ConfirmOrderUserCase(repository);
        this.payOrderUserCase = new PayOrderUserCase(repository);
        this.cancelOrderUserCase = new CancelOrderUserCase(repository);

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

