package com.tienda.model;
import com.tienda.exception.EmptyOrderException;
import com.tienda.exception.InvalidOrderStateException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Represents a customer order in the system.
 *
 * <p>An Order follows a defined lifecycle:</p>
 * <pre>
 * CREATED → CONFIRMED → PAID → SENT → DELIVERED
 *                 ↓
 *             CANCELED  (from CREATED or CONFIRMED)
 *                 ↑
 *             refund()  (from PAID or SENT)
 * </pre>
 *
 * <p>Business rules:</p>
 * <ul>
 *   <li>Items can only be added in CREATED state.</li>
 *   <li>An order must contain at least one item to be confirmed.</li>
 *   <li>An order must be CONFIRMED before being paid.</li>
 *   <li>Only CREATED or CONFIRMED orders can be canceled.</li>
 *   <li>Only PAID or SENT orders can be refunded.</li>
 * </ul>
 */

public class Order {

    private final String id;
    private final Customer customer;
    private final List<ItemOrder> items;
    private OrderState state;

    /**
     * Defines the possible states of an order lifecycle.
     */

    public enum OrderState {
        CREATED,
        CONFIRMED,
        PAID,
        SENT,
        DELIVERED,
        CANCELED
    }

    /**
     * Creates a new Order in CREATED state.
     *
     * @param id       unique identifier of the order
     * @param customer customer who owns the order
     * @throws NullPointerException if id or customer is null
     */


    public Order (String id, Customer customer) {

        this.id = Objects.requireNonNull(id, "Order id cannot be null");
        this.customer = Objects.requireNonNull(customer, "Customer cannot be null");
        this.items = new ArrayList<>();
        this.state = OrderState.CREATED;

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
            throw new InvalidOrderStateException("Items can only be added in CREATED state, current state: " + state);
        }

        items.add(item);
    }

    /**
     * Confirms the order.
     *
     * <p>The order must be in CREATED state and contain at least one item.</p>
     *
     * @throws EmptyOrderException        if the order has no items
     * @throws InvalidOrderStateException if the order is not in CREATED state
     */

    public void confirm () {

        if (items.isEmpty()) {
            throw new EmptyOrderException("Cannot confirm an empty order");
        }

        if (state != OrderState.CREATED) {
            throw new InvalidOrderStateException("Order must be in CREATED state to confirm, current state: " + state);
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
            throw new InvalidOrderStateException("Order must be CONFIRMED to be paid, current state: " + state);
        }

        state = OrderState.PAID;
    }

    /**
     * Cancels the order.
     *
     * <p>An order can only be canceled if it is in CREATED or CONFIRMED state.</p>
     *
     * @throws InvalidOrderStateException if cancellation is not allowed
     */

    public void cancel () {

        if (state != OrderState.CREATED && state != OrderState.CONFIRMED) {
            throw new InvalidOrderStateException("Cannot cancel order in state: " + state);
        }

        state = OrderState.CANCELED;

    }

    /**
     * Requests a refund for the order.
     *
     * <p>A refund can only be requested if the order has been paid or is already sent.</p>
     *
     * @throws InvalidOrderStateException if the order is not in a refundable state
     */

    public void refund () {
        if (!isRefundable()) {
            throw new InvalidOrderStateException("Order is not refundable in state: " + state);
        }

        state = OrderState.CANCELED;
    }

    /**
     * Checks whether the order is eligible for a refund.
     *
     * <p>An order is refundable if it has been paid or is already in transit.</p>
     *
     * @return true if the order is in PAID or SENT state
     */

    public boolean isRefundable () {
        return state == OrderState.PAID || state == OrderState.SENT;
    }

    /**
     * Calculates the total price of all items without applying discounts.
     *
     * @return total amount of all order items
     */

    public BigDecimal getTotal () {
        return items.stream().map(ItemOrder::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    /**
     * Calculates the total price after applying the customer's discount strategy.
     *
     * @return total amount after discount
     * @see com.tienda.discount.DiscountStrategy
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
     * Returns an unmodifiable view of the order items.
     *
     * <p>The returned list cannot be modified directly.
     * Use {@link #addItem(ItemOrder)} to add items.</p>
     *
     * @return immutable list of order items
     */

    public List<ItemOrder> getItems () {
        return Collections.unmodifiableList(items);
    }
}
