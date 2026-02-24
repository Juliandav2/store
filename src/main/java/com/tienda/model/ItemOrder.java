package com.tienda.model;
import java.math.BigDecimal;

/**
 * Represents an item inside an order.
 *
 * <p>
 * Each item contains product information, quantity,
 * and unit price. The subtotal is derived from these values.
 * </p>
 *
 * <p>
 * This entity is part of the Order aggregate and should not
 * be modified outside the Order root.
 * </p>
 */

public class ItemOrder {

    private final Product product;
    private final int amount;
    private final BigDecimal unitPrice;

    /**
     * Creates a new order item.
     *
     * @param product name of the product
     * @param unitPrice price per unit
     * @param amount quantity ordered
     */

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

    /**
     * Calculates subtotal for this item.
     *
     * @return unitPrice multiplied by quantity
     */

    public BigDecimal getSubtotal () {
        return unitPrice.multiply(BigDecimal.valueOf(amount));
    }

    /**
     * @return product ordered
     */

    public Product getProduct () {
        return product;
    }

    /**
     * @return amount ordered
     */

    public int getAmount () {
        return amount;
    }

    /**
     * @return unit price
     */

    public BigDecimal getUnitPrice () {
        return unitPrice;
    }
 }
