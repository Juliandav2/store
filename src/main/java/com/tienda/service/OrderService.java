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

        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("Order ID cannot be null or empty");

        }

        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");

        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Product price must be greater than zero");
        }

        Order order = getOrder (orderId);
        ItemOrder item = new ItemOrder(product, quantity, product.getPrice());

        order.addItem(item);

    }

    public void confirmOrder (String orderId) {
        Order order = getOrder(orderId);
        order.Confirm();
    }

    public void paidOrder (String orderId) {
        Order order = getOrder(orderId);
        order.Paid();
    }


    private Order getOrder (String id) {

        return repository.findById(id).orElseThrow(() -> new NoSuchElementException("Order not found"));

    }
}
