package com.tienda.dto;

import com.tienda.model.Product;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductResponse implements Serializable {

    private final String id;
    private final String name;
    private final BigDecimal price;

    public ProductResponse (Product product) {

        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
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
