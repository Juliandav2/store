package com.tienda.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
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

@Entity
@Table (name = "order_items")
public class ItemOrder {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "product_id", nullable = false)
    private Product product;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "order_id", nullable = false)
    private Order order;

    @Column (nullable = false)
    private int amount;

    @Column (name = "unit_price", nullable = false, precision = 19, scale = 2)
    private BigDecimal unitPrice;

    /**
     * No-args constructor required by JPA.
     */

    protected ItemOrder () {}

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

    public void setOrder(Order order) {
        this.order = order;
    }
}
