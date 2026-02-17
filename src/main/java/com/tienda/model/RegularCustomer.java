package com.tienda.model;

import java.math.BigDecimal;

public class RegularCustomer extends Customer {

    public RegularCustomer (String id, String name) {
        super(id, name);
    }

    @Override
    public BigDecimal applyDiscount (BigDecimal total) {
        return total;
    }

}
