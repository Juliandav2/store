package com.tienda.discount;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class DiscountStrategyTest {

    // ─── NoDiscount ───────────────────────────────────────────

    @Test
    void shouldReturnSameTotalWithNoDiscount() {
        DiscountStrategy strategy = new NoDiscount();
        assertEquals(new BigDecimal("1000.00"), strategy.applyDiscount(new BigDecimal("1000")));
    }

    @Test
    void shouldNormalizeTwoDecimalsWithNoDiscount() {
        DiscountStrategy strategy = new NoDiscount();
        assertEquals(new BigDecimal("99.90"), strategy.applyDiscount(new BigDecimal("99.9")));
    }

    // ─── RegularDiscount ──────────────────────────────────────

    @Test
    void shouldApplyFivePercentForRegularCustomer() {
        DiscountStrategy strategy = new RegularDiscount();
        // 1000 - 5% = 950.00
        assertEquals(new BigDecimal("950.00"), strategy.applyDiscount(new BigDecimal("1000")));
    }

    @Test
    void shouldRoundCorrectlyForRegularDiscount() {
        DiscountStrategy strategy = new RegularDiscount();
        // 100.99 * 0.95 = 95.9405 → 95.94
        assertEquals(new BigDecimal("95.94"), strategy.applyDiscount(new BigDecimal("100.99")));
    }

    // ─── PremiumDiscount ──────────────────────────────────────

    @Test
    void shouldApplyTenPercentForPremiumCustomer() {
        DiscountStrategy strategy = new PremiumDiscount();
        // 1000 - 10% = 900.00
        assertEquals(new BigDecimal("900.00"), strategy.applyDiscount(new BigDecimal("1000")));
    }

    @Test
    void shouldRoundCorrectlyForPremiumDiscount() {
        DiscountStrategy strategy = new PremiumDiscount();
        // 100.99 * 0.90 = 90.891 → 90.89
        assertEquals(new BigDecimal("90.89"), strategy.applyDiscount(new BigDecimal("100.99")));
    }
}
