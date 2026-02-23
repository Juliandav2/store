package com.tienda.dto;
import java.math.BigDecimal;

public class AddProductRequest {

    private final String orderId;
    private final String productId;
    private final String productName;
    private final BigDecimal price;
    private final int quantity;

    public AddProductRequest (String orderId, String productId, String productName, BigDecimal price, int quantity) {

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
