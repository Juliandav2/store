package com.tienda.exepcion;

public class OrderNotFoundException extends BusinessException {

    public OrderNotFoundException (String orderId) {
        super("Order not found with id: " + orderId);
    }
}
