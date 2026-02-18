package com.tienda.service;
import com.tienda.model.*;
import com.tienda.repository.OrderRepository;
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
