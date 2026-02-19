package com.tienda.discount;

import java.math.BigDecimal;

public class RegularDiscount implements DiscountStrategy {

    @Override
    public BigDecimal applyDiscount (BigDecimal total) {
        return total.multiply(new BigDecimal("0.95"));
    }
}
