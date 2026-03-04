package com.tienda.model;

import com.tienda.discount.DiscountStrategy;
import com.tienda.discount.RegularDiscount;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

/**
 * Represents a regular customer.
 *
 * <p>
 * Regular customers receive a standard discount
 * defined by the {@link RegularDiscount} strategy.
 * </p>
 */

@Entity
@DiscriminatorValue("REGULAR")
public class RegularCustomer extends Customer {

     @Transient
    private final DiscountStrategy strategy = new RegularDiscount();

    /**
     * No-args constructor required by JPA.
     */

    protected RegularCustomer () {
        super();
    }

    public RegularCustomer (String id, String name) {
        super(id, name);

    }

    @Override
    public DiscountStrategy getDiscountStrategy () {
        return strategy;
    }

}
