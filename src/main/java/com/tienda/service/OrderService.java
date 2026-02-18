package com.tienda.service;
import com.tienda.model.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.*;


public class OrderService {

    private final Map<String, Order> orders = new HashMap<>();

    public Order createOrder (Customer customer) {

        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        String id = UUID.randomUUID().toString();
        Order order = new Order(id, customer);

        orders.put(id, order);
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


    private Order getOrder (String orderId) {

        Order order = orders.get(orderId);

        if (order == null) {
            throw new NoSuchElementException("Order not found");
        }

        return order;
    }
}
