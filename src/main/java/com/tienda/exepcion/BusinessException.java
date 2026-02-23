package com.tienda.exepcion;

public abstract class BusinessException extends RuntimeException {

    public BusinessException (String message) {
        super(message);
    }
}
