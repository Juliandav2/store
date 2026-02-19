package com.tienda.discount;
import java.math.BigDecimal;

public interface DiscountStrategy {

    BigDecimal applyDiscount (BigDecimal total);

}
