package com.tienda.model;
import java.math.BigDecimal;

/**
 * Represents a product available for purchase.
 *
 * <p>
 * A Product contains identity, name and current price.
 * Price modifications are validated to preserve
 * domain invariants.
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

    public String getId () {
        return id;
    }

    public String getName () {
        return name;
    }

    public BigDecimal getPrice () {
        return price;
    }

    public void updatePrice (BigDecimal newPrice) {
        this.price = validatePrice(newPrice);
    }

}