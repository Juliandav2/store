package com.tienda.model;
import com.tienda.exepcion.EmptyOrderException;
import com.tienda.exepcion.InvalidOrderStateException;
import java.math.BigDecimal;
import java.util.*;


public class Order {

    private final String id;
    private final Customer customer;
    private final List<ItemOrder> items;
    private OrderState state;

    public Order (String id, Customer customer) {

        this.id = Objects.requireNonNull(id);
        this.customer = Objects.requireNonNull(customer);
        this.items = new ArrayList<>();
        this.state = OrderState.CREATED;

    }

    public enum OrderState {
        CREATED,
        CONFIRMED,
        PAID,
        SENT,
        DELIVERED,
        CANCELED
    }

    public void addItem (ItemOrder item) {

        if (state != OrderState.CREATED) {
            throw new InvalidOrderStateException("Only items in the CREATED state can be added");
        }

        items.add(item);

    }

    public void confirm () {

        if (items.isEmpty()) {
            throw new EmptyOrderException("Order cannot be empty");
        }

        if (state != OrderState.CREATED) {
            throw new InvalidOrderStateException("Invalid state: " + state);
        }

        state = OrderState.CONFIRMED;

    }

    public void pay() {

        if (state != OrderState.CONFIRMED) {
            throw new InvalidOrderStateException("Order must be CONFIRMED to be paid");
        }

        state = OrderState.PAID;
    }

    public void cancel () {

        if (state == OrderState.CANCELED) {
            throw new IllegalStateException("Order is already canceled");
        }

        if (state == OrderState.PAID || state == OrderState.SENT || state == OrderState.DELIVERED)
            throw new IllegalStateException("Cannot be order in state " + state);

        state = OrderState.CANCELED;

    }

    public boolean isRefundable () {
        return state == OrderState.PAID || state == OrderState.SENT;
    }

    public BigDecimal getTotal () {
        return items.stream().map(ItemOrder::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    public BigDecimal getTotalWithDiscount () {
        return customer.getDiscountStrategy().applyDiscount(getTotal());

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
