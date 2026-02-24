package com.tienda.model;
import com.tienda.discount.DiscountStrategy;
import com.tienda.discount.RegularDiscount;

/**
 * Represents a premium customer.
 *
 * <p>
 * Premium customers receive a special discount
 * defined by PremiumDiscount strategy.
 * </p>
 */

public class RegularCustomer extends Customer {

    private final DiscountStrategy strategy = new RegularDiscount();

    public RegularCustomer (String id, String name) {
        super(id, name);

    }

    @Override
    public DiscountStrategy getDiscountStrategy () {
        return strategy;
    }

}
