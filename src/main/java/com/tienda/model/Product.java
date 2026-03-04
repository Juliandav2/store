package com.tienda.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

/**
 * Represents a product available in the store.
 *
 * <p>
 * A Product is identified by a unique id and holds
 * a name and a monetary price.
 * </p>
 *
 * <p>Domain invariants:</p>
 * <ul>
 *     <li>id cannot be null or blank</li>
 *     <li>name cannot be null or blank</li>
 *     <li>price cannot be null or negative</li>
 * </ul>
 */

@Entity
@Table (name = "products")
public class Product {

    @Id
    @Column (nullable = false)
    private String id;

    @Column (nullable = false)
    private String name;

    @Column (nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    /**
     * No-args constructor required by JPA.
     */

    protected Product () {}

    /**
     * Creates a new Product with the given id, name and price.
     *
     * @param id    unique product identifier
     * @param name  product display name
     * @param price product price, cannot be negative
     * @throws IllegalArgumentException if id or name are null or blank,
     *                                  or if price is null or negative
     */

    public Product (String id, String name, BigDecimal price) {

        this.id = validateId (id);
        this.name = validateName (name);
        this.price = validatePrice (price);

    }

    private String validateId (String id) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Product id cannot be null or blank");
        }

        return id;
    }

    private String validateName (String name) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or blank");
        }

        return name;
    }

    private BigDecimal validatePrice (BigDecimal price) {

        if (price == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }

        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        return price;
    }


    /**
     * Updates the product price.
     *
     * @param newPrice the new price to assign, cannot be negative
     * @throws IllegalArgumentException if the price is null or negative
     */

    public void updatePrice (BigDecimal newPrice) {
        this.price = validatePrice(newPrice);
    }

    /**
     * @return unique product identifier
     */

    public String getId () {
        return id;
    }

    /**
     * @return product display name
     */

    public String getName () {
        return name;
    }

    /**
     * @return current product price
     */

    public BigDecimal getPrice () {
        return price;
    }
}