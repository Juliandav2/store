package com.tienda.exception;

public class OrderNotFoundException extends BusinessException {

    private final String orderId;

    public OrderNotFoundException (String orderId) {
        super("Order not found with id: " + orderId);
        this.orderId = orderId;
    }

    public String getOrderId () {
        return orderId;
    }
}
