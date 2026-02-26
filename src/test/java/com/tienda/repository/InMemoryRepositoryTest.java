package com.tienda.repository;

import com.tienda.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class InMemoryRepositoryTest {

    private InMemoryOrderRepository repository;
    private Order order;

    @BeforeEach
    void setUp() {
        repository = new InMemoryOrderRepository();
        Customer customer = new RegularCustomer("1", "Julian");
        order = new Order("1", customer);
    }

    // ─── save() ───────────────────────────────────────────────

    @Test
    void shouldSaveOrderSuccessfully() {
        repository.save(order);
        Optional<Order> result = repository.findById("1");
        assertTrue(result.isPresent());
    }

    @Test
    void shouldReplaceOrderWhenSavedWithSameId() {
        repository.save(order);

        Customer customer2 = new RegularCustomer("2", "Andrea");
        Order order2 = new Order("1", customer2);
        repository.save(order2);

        assertEquals("Andrea", repository.findById("1").get().getCustomer().getName());
    }

    @Test
    void shouldThrowWhenSavingNullOrder() {
        assertThrows(NullPointerException.class, () -> repository.save(null));
    }

    // ─── findById() ───────────────────────────────────────────

    @Test
    void shouldReturnOrderWhenFound() {
        repository.save(order);
        Optional<Order> result = repository.findById("1");
        assertTrue(result.isPresent());
        assertEquals("1", result.get().getId());
    }

    @Test
    void shouldReturnEmptyWhenOrderNotFound() {
        Optional<Order> result = repository.findById("999");
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldThrowWhenFindingByNullId() {
        assertThrows(NullPointerException.class, () -> repository.findById(null));
    }

    // ─── deleteAll() ──────────────────────────────────────────

    @Test
    void shouldDeleteAllOrders() {
        repository.save(order);
        repository.deleteAll();
        assertTrue(repository.findById("1").isEmpty());
    }
}
