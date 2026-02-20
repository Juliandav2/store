package com.tienda.service;
import com.tienda.model.*;
import com.tienda.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.*;


public class OrderService {

    private final OrderRepository repository;

    public OrderService (OrderRepository repository) {
        this.repository = repository;
    }

    public Order createOrder (Customer customer) {

        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        String id = UUID.randomUUID().toString();
        Order order = new Order(id, customer);

        repository.save(order);
        return order;

    }

    public void addProduct (String orderId, Product product, int quantity) {

        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");

        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater tahan 0");
        }

        Order order = getOrder(orderId);

        if (order.getState() != Order.OrderState.CREATED) {
            throw new IllegalStateException("Cannot add products to order in state " + order.getState());
        }

        ItemOrder item = new ItemOrder(product, quantity, product.getPrice());
        order.addItem(item);

        boolean exists = order.getItems().stream().anyMatch(i -> i.getProduct().getId().equals(product.getId()));

        if (exists) {
            throw new IllegalArgumentException("Product already added to order");
        }
    }

    public void confirmOrder (String orderId) {
        Order order = getOrder(orderId);
        order.confirm();
    }

    public void paidOrder (String orderId) {
        Order order = getOrder(orderId);
        order.pay();
    }

    public void cancelOrder (String orderId) {
        Order order = getOrder(orderId);
        order.cancel();
    }

    private Order getOrder (String id) {

        return repository.findById(id).orElseThrow(() -> new NoSuchElementException("Order not found"));

    }
}
