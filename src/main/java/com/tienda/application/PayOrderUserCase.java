package com.tienda.application;
import com.tienda.model.Order;
import com.tienda.repository.OrderRepository;
import com.tienda.service.OrderService;

public class PayOrderUserCase {

    private final OrderRepository repository;

    public PayOrderUserCase (OrderRepository repository) {
        this.repository = repository;
    }

    public void execute (String orderId) {

        Order order = repository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.pay();
    }
}
