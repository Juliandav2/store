package com.tienda.exepcion;

/**
 * Base exception to all business rule violations within the domain layer.
 *
 * <p>
 * All custom domain exceptions should extend this class to ensure
 * consistent error handling across application and infrastructure layers.
 * </p>
 *
 * <p>
 * This exception represents predictable business errors and should not be
 * used for technical failures (e.g., database connectivity issues).
 * </p>
 */

public abstract class BusinessException extends RuntimeException {

    /**
     * Creates a new business exception with a descriptive message.
     *
     * @param message explanation of the business rule violation
     */

    public BusinessException (String message) {
        super(message);
    }
}
