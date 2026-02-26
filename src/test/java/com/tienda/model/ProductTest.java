package com.tienda.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    void shouldCreateProductSuccessfully() {
        Product product = new Product("1", "PC Gamer", new BigDecimal("1000"));
        assertEquals("1", product.getId());
        assertEquals("PC Gamer", product.getName());
        assertEquals(new BigDecimal("1000"), product.getPrice());
    }

    @Test
    void shouldThrowWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product(null, "PC Gamer", new BigDecimal("1000")));
    }

    @Test
    void shouldThrowWhenIdIsBlank() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product("   ", "PC Gamer", new BigDecimal("1000")));
    }

    @Test
    void shouldThrowWhenNameIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product("1", null, new BigDecimal("1000")));
    }

    @Test
    void shouldThrowWhenNameIsBlank() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product("1", "   ", new BigDecimal("1000")));
    }

    @Test
    void shouldThrowWhenPriceIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product("1", "PC Gamer", null));
    }

    @Test
    void shouldThrowWhenPriceIsNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product("1", "PC Gamer", new BigDecimal("-1")));
    }

    @Test
    void shouldAllowZeroPrice() {
        assertDoesNotThrow(() -> new Product("1", "Gift", BigDecimal.ZERO));
    }

    // ─── updatePrice() ────────────────────────────────────────

    @Test
    void shouldUpdatePriceSuccessfully() {
        Product product = new Product("1", "PC Gamer", new BigDecimal("1000"));
        product.updatePrice(new BigDecimal("1200"));
        assertEquals(new BigDecimal("1200"), product.getPrice());
    }

    @Test
    void shouldThrowWhenUpdatingWithNullPrice() {
        Product product = new Product("1", "PC Gamer", new BigDecimal("1000"));
        assertThrows(IllegalArgumentException.class,
                () -> product.updatePrice(null));
    }

    @Test
    void shouldThrowWhenUpdatingWithNegativePrice() {
        Product product = new Product("1", "PC Gamer", new BigDecimal("1000"));
        assertThrows(IllegalArgumentException.class,
                () -> product.updatePrice(new BigDecimal("-500")));
    }
}
