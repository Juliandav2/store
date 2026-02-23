package com.tienda.exepcion;

public class InvalidOrderStateException extends BusinessException {

    public InvalidOrderStateException (String message) {
        super(message);
    }
}
