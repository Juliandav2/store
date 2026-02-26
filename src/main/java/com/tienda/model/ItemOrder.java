package com.tienda.model;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents an item inside an order.
 *
 * <p>
 * Each item holds a reference to a product, the quantity ordered,
 * and the unit price at the moment of purchase. The unit price is
 * captured at order time to avoid price fluctuation issues.
 * </p>
 *
 * <p>
 * This entity is part of the Order aggregate and should not
 * be modified outside the Order root.
 * </p>
 *
 * <p>Domain invariants:</p>
 * <ul>
 *     <li>product cannot be null</li>
 *     <li>amount must be greater than zero</li>
 *     <li>unitPrice cannot be null or negative</li>
 * </ul>
 */

public class ItemOrder {

    private final Product product;
    private final int amount;
    private final BigDecimal unitPrice;

    /**
     * Creates a new order item.
     *
     * @param product   the product being ordered
     * @param amount    quantity ordered, must be greater than zero
     * @param unitPrice price per unit at the moment of purchase, cannot be negative
     * @throws NullPointerException     if product or unitPrice is null
     * @throws IllegalArgumentException if amount is zero or less, or unitPrice is negative
     */

    public ItemOrder (Product product, int amount, BigDecimal unitPrice) {

        this.product = Objects.requireNonNull(product, "Product cannot be null");
        this.unitPrice = Objects.requireNonNull(unitPrice, "Unit price cannot be null");

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        if (unitPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Unit price cannot be negative");
        }

        this.amount = amount;

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
