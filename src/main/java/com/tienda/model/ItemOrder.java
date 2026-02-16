package com.tienda.model;
import java.math.BigDecimal;
import java.util.Objects;

public class ItemOrder {

    private final Product product;
    private final int amount;
    private final BigDecimal unitPrice;

    public ItemOrder (Product product, int amount, BigDecimal unitPrice) {

        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("The quantity must be greater than 0");
        }

        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("The price must be greater than 0");
        }

        this.product = product;
        this.amount = amount;
        this.unitPrice = unitPrice;

    }

    public BigDecimal calculateSubtotal () {
        return unitPrice.multiply(BigDecimal.valueOf(amount));
    }

    public Product getProduct () {
        return product;
    }

    public int getAmount () {
        return amount;
    }

    public BigDecimal getUnitPrice () {
        return unitPrice;
    }
 }
