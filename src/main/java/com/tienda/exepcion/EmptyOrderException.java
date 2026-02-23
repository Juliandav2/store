package com.tienda.exepcion;

public class EmptyOrderException extends BusinessException {

    public EmptyOrderException (String message) {
        super(message);
    }
}
