package com.tienda.discount;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

public class PremiumDiscountTest {

    @Test
    void shouldApply10PercentDiscount () {
        DiscountStrategy discount = new PremiumDiscount();
        BigDecimal total = new BigDecimal("100");
        BigDecimal result = discount.applyDiscount(total);

        assertEquals(new BigDecimal("90.00"), result);
    }

}
