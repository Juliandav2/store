package com.tienda.exepcion;

public class EmptyOrderException extends RuntimeException {

    public EmptyOrderException (String message) {
        super(message);
    }
}
