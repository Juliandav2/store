package com.tienda.discount;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Discount strategy for premium customers.
 *
 * <p>
 * Applies a 10% discount to the total purchase amount.
 * </p>
 *
 * <p>
 * The result is rounded to two decimal places using
 * {@link RoundingMode#HALF_UP}, ensuring financial precision.
 * </p>
 */

public class PremiumDiscount implements DiscountStrategy {

    /**
     * Discount strategy for premium customers.
     *
     * <p>
     * Applies a 10% discount to the total purchase amount.
     * </p>
     *
     * <p>
     * The result is rounded to two decimal places using
     * {@link RoundingMode#HALF_UP}, ensuring financial precision.
     * </p>
     */

    @Override
    public BigDecimal applyDiscount (BigDecimal total) {
        return total.multiply(new BigDecimal("0.90")).setScale(2, RoundingMode.HALF_UP);
    }

}
