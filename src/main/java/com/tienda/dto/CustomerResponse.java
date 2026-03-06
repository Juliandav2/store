package com.tienda.dto;

import com.tienda.model.Customer;

import java.util.Locale;

public class CustomerResponse {

    private final String id;
    private final String name;
    private final String type;

    public CustomerResponse (Customer customer) {

        this.id = customer.getId();
        this.name = customer.getName();
        this.type = customer.getClass().getSimpleName().replace("Customer", "").toUpperCase();
    }

    public String getId () {
        return id;
    }

    public String getName () {
        return name;
    }

    public String getType () {
        return type;
    }
}
