package com.tienda.application;

import com.tienda.exception.EmptyOrderException;
import com.tienda.exception.OrderNotFoundException;
import com.tienda.model.*;
import com.tienda.repository.InMemoryOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class ConfirmOrderUseCaseTest {

    private ConfirmOrderUseCase useCase;
    private InMemoryOrderRepository repository;
    private String orderId;

    @BeforeEach
    void setUp() {
        repository = new InMemoryOrderRepository();
        useCase = new ConfirmOrderUseCase(repository);

        Customer customer = new RegularCustomer("1", "Julian");
        Order order = new Order("order-1", customer);
        repository.save(order);
        orderId = order.getId();
    }

    @Test
    void shouldConfirmOrderWithItems() {
        // Primero agregamos un item directamente en el dominio
        Order order = repository.findById(orderId).get();
        Product product = new Product("p1", "Mouse", new BigDecimal("50"));
        order.addItem(new ItemOrder(product, 1, product.getPrice()));
        repository.save(order);

        useCase.execute(orderId);

        assertEquals(Order.OrderState.CONFIRMED, repository.findById(orderId).get().getState());
    }

    @Test
    void shouldThrowWhenOrderIsEmpty() {
        assertThrows(EmptyOrderException.class, () -> useCase.execute(orderId));
    }

    @Test
    void shouldThrowWhenOrderIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> useCase.execute(null));
    }

    @Test
    void shouldThrowWhenOrderNotFound() {
        assertThrows(OrderNotFoundException.class, () -> useCase.execute("id-inexistente"));
    }
}
