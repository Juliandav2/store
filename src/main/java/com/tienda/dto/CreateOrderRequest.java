package com.tienda.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    private final String customerType;

    /**
     * Creates a validated order creation request.
     *
     * @param customerId   the unique identifier of the customer
     * @param customerName the name of the customer
     * @param customerType the type of customer, either "REGULAR" or "PREMIUM"
     * @throws IllegalArgumentException if any value is null or blank
     */

    @JsonCreator
    public CreateOrderRequest (@JsonProperty String customerId, @JsonProperty String customerName,@JsonProperty String customerType) {

        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("Customer ID cannot be null or empty");
        }

        if (customerName == null || customerName.isBlank()) {
            throw new IllegalArgumentException("Customer Name cannot be null or empty");
        }

        if (customerType == null || customerType.isBlank()) {
            throw new IllegalArgumentException("Customer type cannot be null or blank");
        }

        this.customerId = customerId;
        this.customerName = customerName;
        this.customerType = customerType;

    }

    /**
     * @return unique customer identifier
     */

    public String getCustomerId () {
        return customerId;
    }

    /**
     * @return customer display name
     */

    public String getCustomerName() {
        return customerName;
    }

    /**
     * @return customer type, either "REGULAR" or "PREMIUM"
     */

    public String getCustomerType () {
        return customerType;
    }
}

