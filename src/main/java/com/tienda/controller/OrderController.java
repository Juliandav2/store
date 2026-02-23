package com.tienda.controller;
import com.tienda.dto.*;
import com.tienda.mapper.OrderMapper;
import com.tienda.model.*;
import com.tienda.service.OrderService;

public class OrderController {

    private final OrderService service;

    public OrderController (OrderService service) {
        this.service = service;
    }

    public OrderResponse createOrder (CreateOrderRequest request) {

        Customer customer = new RegularCustomer(request.getCustomerId(), request.getCustomerName());

        Order order = service.createOrder(customer);
        return OrderMapper.toResponse(order);
    }

    public void addProduct (AddProductRequest request) {

        Product product = new Product(request.getProductId(), request.getProductName(), request.getPrice());
        service.addProduct(request.getOrderId(), product, request.getQuantity());
    }

    public void confirm (String orderId) {
        service.confirm(orderId);
    }

    public void pay (String orderId) {
        service.pay(orderId);
    }

    public void cancel (String orderId) {
        service.cancel(orderId);
    }


}
