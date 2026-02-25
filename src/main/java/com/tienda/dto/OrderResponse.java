package com.tienda.dto;
import java.math.BigDecimal;

/**
 * Data Transfer Object representing the response returned
 * after processing order-related operations.
 *
 * <p>
 * This object is used to expose order information to external layers
 * without leaking internal domain entities.
 * </p>
 *
 * <p>
 * It contains a snapshot of:
 * <ul>
 *     <li>Order identifier</li>
 *     <li>Current order state</li>
 *     <li>Total amount</li>
 * </ul>
 * </p>
 */

public class OrderResponse {

    private final String id;
    private final String state;
    private final BigDecimal total;

    /**
     * Creates an immutable representation of an order response.
     *
     * @param id the order identifier
     * @param state the current state of the order
     * @param total the total monetary amount of the order
     */


    public OrderResponse (String id, String state, BigDecimal total) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id cannot be null or blank");
        }

        if (state == null || state.isBlank()) {
            throw new IllegalStateException("State cannot be null or blank");
        }

        if (total == null) {
            throw new IllegalArgumentException("total cannot be null");
        }

        this.id = id;
        this.state = state;
        this.total = total;

    }

    public String getId () {
        return id;
    }

    public String getState () {
        return state;
    }

    public BigDecimal getTotal () {
        return total;
    }
 }
