package com.tienda.exepcion;

public class InvalidOrderStateException extends RuntimeException {

    public InvalidOrderStateException (String message) {
        super(message);
    }
}
