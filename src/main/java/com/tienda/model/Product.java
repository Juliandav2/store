package com.tienda.model;
import java.math.BigDecimal;

/**
 * Represents a product available in the store domain.
 *
 * <p>
 * A Product is an aggregate element identified by a unique id,
 * with a name and a monetary price.
 * </p>
 *
 * <p>
 * This class enforces basic domain invariants:
 * <ul>
 *     <li> id cannot be null or blank</li>
 *     <li> name cannot be null or blank</li>
 *     <li> price cannot be null or negative</li>
 * </ul>
 * </p>
 *
 * <p>
 * Price updates are validated to ensure the domain remains consistent.
 * </p>
 */

public class Product {

    private final String id;
    private final String name;
    private BigDecimal price;

    public Product (String id, String name, BigDecimal price) {

        this.id = validateId (id);
        this.name = validateName (name);
        this.price = validatePrice (price);

    }

    private String validateId (String id) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Product id cannot be null or empty");
        }

        return id;
    }

    private String validateName (String name) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be a null or empty");
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
     * @param newPrice the new price to assign
     * @throws IllegalArgumentException if the price is null or negative
     */

    public void updatePrice (BigDecimal newPrice) {
        this.price = validatePrice(newPrice);
    }

    public String getId () {
        return id;
    }

    public String getName () {
        return name;
    }

    public BigDecimal getPrice () {
        return price;
    }



}