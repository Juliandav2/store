package com.tienda.discount;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Discount strategy for regular customers.
 *
 * <p>
 * Applies a 5% discount to the total purchase amount.
 * </p>
 *
 * <p>
 * The result is rounded to two decimal places using
 * {@link RoundingMode#HALF_UP}.
 * </p>
 */

public class RegularDiscount implements DiscountStrategy {

    /**
     * Applies a 5% discount for regular customers.
     *
     * @param total the original total amount
     * @return the total after applying the regular customer discount
     */

    @Override
    public BigDecimal applyDiscount (BigDecimal total) {
        return total.multiply(new BigDecimal("0.95")).setScale(2, RoundingMode.HALF_UP);
    }
}
