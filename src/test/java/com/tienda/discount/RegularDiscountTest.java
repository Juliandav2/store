package com.tienda.discount;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class RegularDiscountTest {

    @Test
    void shouldApply5PercentDiscount () {
        DiscountStrategy discount = new RegularDiscount();
        BigDecimal total = new BigDecimal("100");
        BigDecimal result = discount.applyDiscount(total);

        assertEquals(0, result.compareTo(new BigDecimal("95.00")));
    }
 }
