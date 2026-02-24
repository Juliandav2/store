package com.tienda.exception;

/**
 * Thrown when an operation is attempted on an order
 * that is not in a valid state for that operation.
 *
 * <p>
 * Example: trying to pay an order that is not confirmed.
 * </p>
 */

public class InvalidOrderStateException extends BusinessException {

    /**
     * Creates a new exception with a detailed explanation.
     *
     * @param message description of the invalid state transition
     */

    public InvalidOrderStateException (String message) {
        super(message);
    }
}
