package com.tienda.model;
import java.math.BigDecimal;
import java.util.Objects;

public class Product {

    private final String id;
    private final String name;
    private BigDecimal price;

    public Product (String id, String name, BigDecimal price) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id cannot be null or empty");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        this.id = Objects.requireNonNull(id, "Id cannot be null");
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.price = Objects.requireNonNull(price, "Price cannot be null");
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

        if (newPrice == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }

        if (newPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        this.price = newPrice;
    }

}
