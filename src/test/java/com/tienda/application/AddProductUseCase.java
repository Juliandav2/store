package com.tienda.application;

import com.tienda.exception.OrderNotFoundException;
import com.tienda.model.*;
import com.tienda.repository.InMemoryOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class AddProductOrderUseCaseTest {

    private AddProductOrderUseCase useCase;
    private InMemoryOrderRepository repository;
    private String orderId;
    private Product product;

    @BeforeEach
    void setUp() {
        repository = new InMemoryOrderRepository();
        useCase = new AddProductOrderUseCase(repository);

        // Creamos una orden base en el repositorio
        Customer customer = new RegularCustomer("1", "Julian");
        Order order = new Order("order-1", customer);
        repository.save(order);
        orderId = order.getId();

        product = new Product("p1", "PC Gamer", new BigDecimal("1000"));
    }

    @Test
    void shouldAddProductToExistingOrder() {
        useCase.execute(orderId, product, 2);

        Order order = repository.findById(orderId).get();
        assertEquals(1, order.getItems().size());
        assertEquals(2, order.getItems().get(0).getAmount());
    }

    @Test
    void shouldThrowWhenOrderIdIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(null, product, 1));
    }

    @Test
    void shouldThrowWhenOrderIdIsBlank() {
        assertThrows(IllegalArgumentException.class,
                () -> useCase.execute("  ", product, 1));
    }

    @Test
    void shouldThrowWhenProductIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(orderId, null, 1));
    }

    @Test
    void shouldThrowWhenQuantityIsZero() {
        assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(orderId, product, 0));
    }

    @Test
    void shouldThrowWhenQuantityIsNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(orderId, product, -1));
    }

    @Test
    void shouldThrowWhenOrderNotFound() {
        assertThrows(OrderNotFoundException.class,
                () -> useCase.execute("id-inexistente", product, 1));
    }
}