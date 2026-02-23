package com.tienda.dto;

public class CreateOrderRequest {

    private final String customerId;
    private final String customerName;

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

