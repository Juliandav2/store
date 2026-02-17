package com.tienda.model;

import java.math.BigDecimal;

public class PremiumCustomer extends Customer {

    private static final BigDecimal DISCOUNT = new BigDecimal("0.10");

    public PremiumCustomer (String id, String name) {
        super(id, name);
    }

    @Override
    public BigDecimal applyDiscount (BigDecimal total) {
        BigDecimal discount = total.multiply(DISCOUNT);
        return total.subtract(discount);
    }
}
