package com.tienda.discount;

import java.math.BigDecimal;

public class NoDiscount implements DiscountStrategy {

    @Override
    public BigDecimal applyDiscount (BigDecimal total) {
        return total;
    }

}
