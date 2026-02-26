package com.tienda.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class ItemOrderTest {

    private final Product product = new Product("1", "PC Gamer", new BigDecimal("1000"));

    // ─── constructor validations ───────────────────────────────

    @Test
    void shouldCreateItemOrderSuccessfully() {
        ItemOrder item = new ItemOrder(product, 2, new BigDecimal("1000"));
        assertEquals(2, item.getAmount());
        assertEquals(new BigDecimal("1000"), item.getUnitPrice());
    }

    @Test
    void shouldThrowWhenProductIsNull() {
        assertThrows(NullPointerException.class,
                () -> new ItemOrder(null, 1, new BigDecimal("1000")));
    }

    @Test
    void shouldThrowWhenAmountIsZero() {
        assertThrows(IllegalArgumentException.class,
                () -> new ItemOrder(product, 0, new BigDecimal("1000")));
    }

    @Test
    void shouldThrowWhenAmountIsNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> new ItemOrder(product, -1, new BigDecimal("1000")));
    }

    @Test
    void shouldThrowWhenUnitPriceIsNull() {
        assertThrows(NullPointerException.class,
                () -> new ItemOrder(product, 1, null));
    }

    @Test
    void shouldThrowWhenUnitPriceIsNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> new ItemOrder(product, 1, new BigDecimal("-1")));
    }

    @Test
    void shouldAllowZeroUnitPrice() {
        assertDoesNotThrow(() -> new ItemOrder(product, 1, BigDecimal.ZERO));
    }

    // ─── getSubtotal() ────────────────────────────────────────

    @Test
    void shouldCalculateSubtotalCorrectly() {
        ItemOrder item = new ItemOrder(product, 3, new BigDecimal("1000"));
        assertEquals(new BigDecimal("3000"), item.getSubtotal());
    }

    @Test
    void shouldReturnZeroSubtotalWhenPriceIsZero() {
        ItemOrder item = new ItemOrder(product, 5, BigDecimal.ZERO);
        assertEquals(BigDecimal.ZERO, item.getSubtotal());
    }
}

