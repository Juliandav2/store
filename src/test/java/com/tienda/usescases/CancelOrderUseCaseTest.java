package com.tienda.usescases;

import com.tienda.application.CancelOrderUseCase;
import com.tienda.exception.OrderNotFoundException;
import com.tienda.model.*;
import com.tienda.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CancelOrderUseCaseTest {

    @Mock
    private OrderRepository orderRepository;

    private CancelOrderUseCase cancelOrderUseCase;

    @BeforeEach
    void setUp() {
        cancelOrderUseCase = new CancelOrderUseCase(orderRepository);
    }

    @Test
    void shouldCancelCreatedOrderSuccessfully() {
        Order order = new Order("o1", new PremiumCustomer("c1", "Julian"));

        when(orderRepository.findById("o1")).thenReturn(Optional.of(order));
        doNothing().when(orderRepository).save(order);

        cancelOrderUseCase.execute("o1");

        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void shouldThrowWhenOrderNotFound() {
        when(orderRepository.findById("nonexistent")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cancelOrderUseCase.execute("nonexistent"))
                .isInstanceOf(OrderNotFoundException.class);
    }

    @Test
    void shouldCancelConfirmedOrderSuccessfully() {
        Order order = new Order("o2", new PremiumCustomer("c1", "Julian"));
        order.addItem(new ItemOrder(
                new Product("p1", "Laptop", new BigDecimal("1500")), 1, new BigDecimal("1500")));
        order.confirm();

        when(orderRepository.findById("o2")).thenReturn(Optional.of(order));
        doNothing().when(orderRepository).save(order);

        cancelOrderUseCase.execute("o2");

        verify(orderRepository, times(1)).save(order);
    }
}