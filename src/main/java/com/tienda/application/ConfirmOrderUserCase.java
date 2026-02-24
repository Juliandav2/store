package com.tienda.application;
import com.tienda.model.Order;
import com.tienda.repository.OrderRepository;

import java.util.NoSuchElementException;

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
