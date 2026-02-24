package com.tienda.model;
import com.tienda.discount.DiscountStrategy;
import com.tienda.discount.PremiumDiscount;

/**
 * Represents a product available for purchase.
 *
 * <p>
 * A Product contains identity, name and current price.
 * Price modifications are validated to preserve
 * domain invariants.
 * </p>
 */

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
