package com.tienda.exception;

/**
 * Thrown when an order operation requires at least one item,
 * but the order is empty.
 *
 * <p>
 * Example: confirming an order without items.
 * </p>
 */

public class EmptyOrderException extends BusinessException {

    /**
     * Thrown when an order operation requires at least one item,
     * but the order is empty.
     *
     * <p>
     * Example: confirming an order without items.
     * </p>
     */

    public EmptyOrderException (String message) {
        super(message);
    }
}
