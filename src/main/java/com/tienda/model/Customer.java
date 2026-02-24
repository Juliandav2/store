package com.tienda.model;
import com.tienda.discount.DiscountStrategy;
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
 * This class is part of the domain layer and should remain
 * independent of any persistence or framework annotations.
 * </p>
 */

public abstract class Customer {

    protected final String id;
    protected final String name;

    /**
     * Creates a new customer.
     *
     * @param id unique customer identifier
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
