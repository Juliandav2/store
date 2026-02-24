package com.tienda.model;
import com.tienda.exception.EmptyOrderException;
import com.tienda.exception.InvalidOrderStateException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Represents a customer order in the system.
 *
 * <p>An Order follows a defined lifecycle:
 * CREATED → CONFIRMED → PAID → SENT → DELIVERED or CANCELED.</p>
 *
 * <p>Business rules:
 * <ul>
 *   <li>Items can only be added in CREATED state.</li>
 *   <li>An order must contain at least one item to be confirmed.</li>
 *   <li>An order must be CONFIRMED before being paid.</li>
 *   <li>Paid or delivered orders cannot be canceled.</li>
 * </ul>
 * </p>
 *
 * <p>This class enforces all domain invariants related to order state transitions.</p>
 */

public class Order {

    private final String id;
    private final Customer customer;
    private final List<ItemOrder> items;
    private OrderState state;

    /**
     * Creates a new Order in CREATED state.
     *
     * @param id unique identifier of the order
     * @param customer customer who owns the order
     * @throws NullPointerException if id or customer is null
     */

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

    /**
     * Adds an item to the order.
     *
     * <p>Items can only be added while the order is in CREATED state.</p>
     *
     * @param item item to be added
     * @throws InvalidOrderStateException if the order is not in CREATED state
     */

    public void addItem (ItemOrder item) {

        if (state != OrderState.CREATED) {
            throw new InvalidOrderStateException("Only items in the CREATED state can be added");
        }

        items.add(item);

    }

    /**
     * Confirms the order if it contains at least one item
     * and is currently in CREATED state.
     *
     * @throws EmptyOrderException if the order has no items
     * @throws InvalidOrderStateException if the order is not in CREATED state
     */

    public void confirm () {

        if (items.isEmpty()) {
            throw new EmptyOrderException("Order cannot be empty");
        }

        if (state != OrderState.CREATED) {
            throw new InvalidOrderStateException("Invalid state: " + state);
        }

        state = OrderState.CONFIRMED;

    }

    /**
     * Marks the order as paid.
     *
     * <p>The order must be in CONFIRMED state before payment.</p>
     *
     * @throws InvalidOrderStateException if the order is not CONFIRMED
     */

    public void pay() {

        if (state != OrderState.CONFIRMED) {
            throw new InvalidOrderStateException("Order must be CONFIRMED to be paid");
        }

        state = OrderState.PAID;
    }

    /**
     * Cancels the order.
     *
     * <p>An order cannot be canceled if it has already been paid,
     * sent, delivered, or already canceled.</p>
     *
     * @throws InvalidOrderStateException if cancellation is not allowed
     */

    public void cancel () {

        if (state == OrderState.CANCELED || state == OrderState.PAID || state == OrderState.SENT || state == OrderState.DELIVERED) {
            throw new InvalidOrderStateException("Cannot cancel order in state " + state);
        }

        state = OrderState.CANCELED;

    }

    public boolean isRefundable () {
        return state == OrderState.PAID || state == OrderState.SENT;
    }

    /**
     * Calculates the total price of the order
     * without applying discounts.
     *
     * @return total amount of all order items
     */

    public BigDecimal getTotal () {
        return items.stream().map(ItemOrder::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    /**
     * Calculates the total price after applying
     * the customer's discount strategy.
     *
     * @return total amount after discount
     */

    public BigDecimal getTotalWithDiscount () {
        return customer.getDiscountStrategy().applyDiscount(getTotal());

    }

    /**
     * @return order unique identifier
     */

    public String getId () {
        return id;

    }

    /**
     * @return current order state
     */

    public Customer getCustomer () {
        return customer;
    }

    /**
     * @return current order state
     */

    public OrderState getState () {
        return this.state;
    }

    /**
     * Returns an unmodifiable list of items.
     *
     * @return immutable list of order items
     */

    public List<ItemOrder> getItems () {
        return Collections.unmodifiableList(items);
    }
}
