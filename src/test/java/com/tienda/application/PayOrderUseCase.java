package com.tienda.application;

import com.tienda.exception.InvalidOrderStateException;
import com.tienda.exception.OrderNotFoundException;
import com.tienda.model.*;
import com.tienda.repository.InMemoryOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class PayOrderUseCaseTest {

    private PayOrderUseCase useCase;
    private InMemoryOrderRepository repository;
    private String orderId;

    @BeforeEach
    void setUp() {
        repository = new InMemoryOrderRepository();
        useCase = new PayOrderUseCase(repository);

        Customer customer = new RegularCustomer("1", "Julian");
        Order order = new Order("order-1", customer);
        Product product = new Product("p1", "Mouse", new BigDecimal("50"));
        order.addItem(new ItemOrder(product, 1, product.getPrice()));
        order.confirm();
        repository.save(order);
        orderId = order.getId();
    }

    @Test
    void shouldPayConfirmedOrder() {
        useCase.execute(orderId);
        assertEquals(Order.OrderState.PAID, repository.findById(orderId).get().getState());
    }

    @Test
    void shouldThrowWhenOrderIsNotConfirmed() {
        // Creamos una orden sin confirmar
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
    void shouldThrowWhenOrderNotFound() {
        assertThrows(OrderNotFoundException.class, () -> useCase.execute("id-inexistente"));
    }
}