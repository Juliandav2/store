package com.tienda.model;
import com.tienda.discount.DiscountStrategy;
import com.tienda.discount.PremiumDiscount;

public class PremiumCustomer extends Customer {

    private final DiscountStrategy strategy = new PremiumDiscount();

    public PremiumCustomer (String id, String name) {
        super(id, name);

    }

    @Override
    public DiscountStrategy getDiscountStrategy () {
        return strategy;
    }
}
