package com.tienda.exception;

/**
 * Thrown when an order with the given id does not exist in the system.
 *
 * <p>
 * This exception carries the id that was searched,
 * useful for building descriptive error responses.
 * </p>
 */

public class OrderNotFoundException extends BusinessException {

    private final String orderId;

    /**
     * Creates a new exception for the given order id.
     *
     * @param orderId the id that was not found
     */

    public OrderNotFoundException (String orderId) {
        super("Order not found with id: " + orderId);
        this.orderId = orderId;
    }

    /**
     * @return the order id that was not found
     */

    public String getOrderId () {
        return orderId;
    }
}
