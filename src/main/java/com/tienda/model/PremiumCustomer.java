package com.tienda.model;

import com.tienda.discount.DiscountStrategy;
import com.tienda.discount.PremiumDiscount;

import java.math.BigDecimal;

public class PremiumCustomer extends Customer {

    private static final BigDecimal DISCOUNT = new BigDecimal("0.10");

    public PremiumCustomer (String id, String name) {
        super(id, name);
    }

    @Override
    public DiscountStrategy getDiscountStrategy () {
        return new PremiumDiscount();
    }
}
