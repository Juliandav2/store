package com.tienda.application;

import com.tienda.exception.InvalidOrderStateException;
import com.tienda.exception.OrderNotFoundException;
import com.tienda.model.*;
import com.tienda.repository.InMemoryOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class RefundOrderUseCaseTest {

    private RefundOrderUseCase useCase;
    private InMemoryOrderRepository repository;
    private String orderId;

    @BeforeEach
    void setUp() {
        repository = new InMemoryOrderRepository();
        useCase = new RefundOrderUseCase(repository);

        Customer customer = new RegularCustomer("1", "Julian");
        Order order = new Order("order-1", customer);
        Product product = new Product("p1", "PC Gamer", new BigDecimal("1000"));
        order.addItem(new ItemOrder(product, 1, product.getPrice()));
        order.confirm();
        order.pay();
        repository.save(order);
        orderId = order.getId();
    }

    @Test
    void shouldRefundPaidOrder() {
        useCase.execute(orderId);
        assertEquals(Order.OrderState.CANCELED, repository.findById(orderId).get().getState());
    }

    @Test
    void shouldThrowWhenOrderIsNotRefundable() {

        Customer customer = new RegularCustomer("2", "Andrea");
        Order newOrder = new Order("order-2", customer);
        repository.save(newOrder);

        assertThrows(InvalidOrderStateException.class, () -> useCase.execute("order-2"));
    }

    @Test
    void shouldThrowWhenOrderIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> useCase.execute(null));
    }

    @Test
    void shouldThrowWhenOrderIdIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> useCase.execute("  "));
    }

    @Test
    void shouldThrowWhenOrderNotFound() {
        assertThrows(OrderNotFoundException.class, () -> useCase.execute("id-inexistente"));
    }
}