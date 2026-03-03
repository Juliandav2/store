package com.tienda.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * Data Transfer Object used to request the addition of a product to an order.
 *
 * <p>
 * This object carries all necessary data from external layers
 * (e.g., controllers) into the application layer.
 * </p>
 *
 * <p>
 * Validation is performed in the constructor to guarantee that
 * the request contains consistent and non-null values before
 * reaching the business logic.
 * </p>
 *
 * <p>
 * Invariants:
 * <ul>
 *     <li>Order ID must not be null or blank</li>
 *     <li>Product ID must not be null or blank</li>
 *     <li>Product name must not be null or blank</li>
 *     <li>Price must not be null or negative</li>
 *     <li>Quantity must be greater than zero</li>
 * </ul>
 * </p>
 */

public class AddProductRequest {

    private final String orderId;
    private final String productId;
    private final String productName;
    private final BigDecimal price;
    private final int quantity;

    /**
     * Creates a validated request for adding a product to an order.
     *
     * @param orderId the identifier of the order
     * @param productId the identifier of the product
     * @param productName the name of the product
     * @param price the unit price of the product
     * @param quantity the quantity to add
     * @throws IllegalArgumentException if any parameter violates validation rules
     */

    @JsonCreator
    public AddProductRequest (@JsonProperty String orderId,@JsonProperty String productId,@JsonProperty String productName,@JsonProperty BigDecimal price,@JsonProperty int quantity) {

        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("Order ID cannot be null or empty");
        }

        if (productId == null || productId.isBlank()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty");
        }

        if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }

        if (price == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }

        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("The quantity must be greater than 0");
        }

        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getOrderId () {
        return orderId;
    }

    public String getProductId () {
        return productId;
    }

    public String getProductName () {
        return productName;
    }

    public BigDecimal getPrice () {
        return price;
    }

    public int getQuantity () {
        return quantity;
    }
}
