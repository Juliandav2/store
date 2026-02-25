package com.tienda.discount;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Discount strategy representing a default or minimal discount policy.
 *
 * <p>
 * Currently applies a 5% reduction to the total amount.
 * </p>
 *
 * <p>
 * The result is rounded to two decimal places using
 * {@link RoundingMode#HALF_UP}, following standard financial rounding rules.
 * </p>
 */

public class NoDiscount implements DiscountStrategy {

    /**
     * Applies a 5% discount to the total.
     *
     * @param total the original total amount
     * @return the total after applying a 5% discount
     */

    @Override
    public BigDecimal applyDiscount (BigDecimal total) {
        return total.multiply(new BigDecimal("0.95")).setScale(2, RoundingMode.HALF_UP);
    }

}
