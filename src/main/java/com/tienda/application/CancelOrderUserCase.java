package com.tienda.application;
import com.tienda.exepcion.OrderNotFoundException;
import com.tienda.model.Order;
import com.tienda.repository.OrderRepository;

public class CancelOrderUserCase {

    private final OrderRepository repository;

    public CancelOrderUserCase (OrderRepository repository) {
        this.repository = repository;
    }

    public void execute (String orderId) {

        Order order = repository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
        order.cancel();
    }
}
