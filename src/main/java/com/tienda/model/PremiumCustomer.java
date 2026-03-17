package com.tienda.model;

import com.tienda.discount.DiscountStrategy;
import com.tienda.discount.PremiumDiscount;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

/**
 * Represents a premium customer.
 *
 * <p>
 * Premium customers receive an exclusive discount
 * defined by the {@link PremiumDiscount} strategy.
 * </p>
 */

@Entity
@DiscriminatorValue("PREMIUM")
public class PremiumCustomer extends Customer {

    @Transient
    private final DiscountStrategy strategy = new PremiumDiscount();

    /**
     * No-args constructor required by JPA.
     */

    protected PremiumCustomer () {
        super();
    }

    public PremiumCustomer (String id, String name) {
        super(id, name);
    }

    @Override
    public DiscountStrategy getDiscountStrategy () {
        return strategy;
    }
}
