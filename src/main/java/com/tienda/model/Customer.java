package com.tienda.model;

import com.tienda.discount.DiscountStrategy;
import jakarta.persistence.*;
import java.util.Objects;

/**
 * Represents a customer within the system.
 *
 * <p>
 * A customer owns orders and defines the discount strategy
 * applied when calculating the final total of an order.
 * </p>
 *
 * <p>
 * Uses SINGLE_TABLE inheritance strategy — all customer types
 * are stored in the same table with a discriminator column.
 * </p>
 */

@Entity
@Table (name = "customers")
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn (name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class Customer {

    @Id
    @Column (nullable = false)
    protected final String id;

    @Column (nullable = false)
    protected final String name;

    /**
     * No-args constructor required by JPA.
     * Should not be used directly.
     */

    protected Customer () {
        this.id = null;
        this.name = null;
    }

    /**
     * Creates a new customer.
     *
     * @param id   unique customer identifier
     * @param name customer name
     */

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
