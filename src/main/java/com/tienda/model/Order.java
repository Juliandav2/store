package com.tienda.model;
import com.tienda.discount.DiscountStrategy;
import com.tienda.exepcion.EmptyOrderException;
import com.tienda.exepcion.InvalidOrderStateException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.ArrayList;


public class Order {

    private final String id;
    private final Customer customer;
    private final List<ItemOrder> items;
    private OrderState state;

    public Order (String id, Customer customer) {

        this.id = Objects.requireNonNull(id);
        this.customer = Objects.requireNonNull(customer);
        this.items = new ArrayList<>();
        this.state = OrderState.CREATE;

    }

    public enum OrderState {
        CREATE,
        CONFIRM,
        PAID,
        SENT,
        DELIVERED,
        CANCELED
    }

    public void addItem (ItemOrder item) {

        if (state != OrderState.CREATE) {
            throw new InvalidOrderStateException("Only items in the CREATED state can be added");
        }

        items.add(item);

    }

    public void Confirm () {

        if (items.isEmpty()) {
            throw new EmptyOrderException("Order cannot be empty");
        }

        if (state != OrderState.CREATE) {
            throw new InvalidOrderStateException("Invalid state: " + state);
        }

        state = OrderState.CONFIRM;

    }

    public void Cancel () {

        if (state == OrderState.PAID || state == OrderState.SENT || state == OrderState.DELIVERED) {
            throw new IllegalStateException("An order cannot be cancelled in this state " + state);
        }

        state = OrderState.CANCELED;

    }

    public void Paid() {
        if (state != OrderState.CONFIRM) {
            throw new InvalidOrderStateException("You can only pay for one confirmed order.");
        }

        state = OrderState.PAID;
    }

    public BigDecimal getTotalWithDiscount () {
        return customer.getDiscountStrategy().applyDiscount(getTotal());
    }

    public BigDecimal getTotal () {

        return items.stream().map(ItemOrder::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    public String getId () {
        return id;
    }

    public Customer getCustomer () {
        return customer;
    }

    public OrderState getState () {
        return this.state;
    }

    public List<ItemOrder> getItems () {
        return Collections.unmodifiableList(items);
    }
}
