package com.tienda.dto;

/**
 * Data Transfer Object used to request the creation of a new order.
 *
 * <p>
 * Encapsulates customer information required to initialize
 * a new order in the system.
 * </p>
 *
 * <p>
 * Validation ensures that customer data is complete
 * before entering the application layer.
 * </p>
 */

public class CreateOrderRequest {

    private final String customerId;
    private final String customerName;

    /**
     * Creates a validated order creation request.
     *
     * @param customerId the unique identifier of the customer
     * @param customerName the name of the customer
     * @throws IllegalArgumentException if any value is null or blank
     */

    public CreateOrderRequest (String customerId, String customerName) {

        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("Customer ID cannot be null or empty");
        }

        if (customerName == null || customerName.isBlank()) {
            throw new IllegalArgumentException("Customer Name cannot be null or empty");
        }

        this.customerId = customerId;
        this.customerName = customerName;

    }

    public String getCustomerId () {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }
}

