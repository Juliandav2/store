package com.tienda.exepcion;

/**
 * Base exception to all business rule violations within the domain.
 *
 * <p>All custom domain and application exceptions should extend this class
 * to ensure consistent error handling across layers.</p>
 */

public abstract class BusinessException extends RuntimeException {

    public BusinessException (String message) {
        super(message);
    }
}
