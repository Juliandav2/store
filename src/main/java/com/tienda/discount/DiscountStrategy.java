package com.tienda.discount;
import java.math.BigDecimal;

/**
 * Strategy interface for applying discounts to a monetary total.
 *
 * <p>
 * This interface follows the Strategy Pattern, allowing different
 * discount policies to be applied dynamically without modifying
 * the client code.
 * </p>
 *
 * <p>
 * Implementations must ensure:
 * <ul>
 *     <li>The returned value is properly scaled for monetary operations</li>
 *     <li>No mutation of the input parameter</li>
 * </ul>
 * </p>
 */

public interface DiscountStrategy {

    /**
     * Applies a discount policy to the given total amount.
     *
     * @param total the original total amount (must not be null)
     * @return the discounted total amount
     */

    BigDecimal applyDiscount (BigDecimal total);

}
