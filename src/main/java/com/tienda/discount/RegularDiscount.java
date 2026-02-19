package com.tienda.discount;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RegularDiscount implements DiscountStrategy {

    @Override
    public BigDecimal applyDiscount (BigDecimal total) {
        return total.multiply(new BigDecimal("0.95")).setScale(2, RoundingMode.HALF_UP);
    }
}
