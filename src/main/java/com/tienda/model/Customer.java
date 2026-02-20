package com.tienda.model;
import com.tienda.discount.DiscountStrategy;
import java.util.Objects;

public abstract class Customer {

    protected final String id;
    protected final String name;

    public Customer (String id, String name) {

        this.id = Objects.requireNonNull(id, "Id cannot be null");
        this.name = Objects.requireNonNull(name, "Name cannot be null");

    }

    public String getId () {
        return id;
    }

    public String getName () {
        return name;
    }

    public abstract DiscountStrategy getDiscountStrategy ();
}
