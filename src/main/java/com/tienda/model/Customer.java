package com.tienda.model;
import java.math.BigDecimal;
import java.util.Objects;

public abstract class Customer {

    protected final String id;
    protected final String name;

    public Customer (String id, String name) {

        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
    }

    public String getId () {
        return id;
    }

    public String getName () {
        return name;
    }

    public abstract BigDecimal applyDiscount (BigDecimal total);
}
