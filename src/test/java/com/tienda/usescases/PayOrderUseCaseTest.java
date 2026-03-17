package com.tienda.usescases;

import com.tienda.application.PayOrderUseCase;
import com.tienda.exception.InvalidOrderStateException;
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
public class PayOrderUseCaseTest {

    @Mock
    private OrderRepository orderRepository;

    private PayOrderUseCase payOrderUseCase;

    @BeforeEach
    void setUp() {
        payOrderUseCase = new PayOrderUseCase(orderRepository);
    }

    @Test
    void shouldPayOrderSuccessfully() {
        Order order = new Order("o1", new PremiumCustomer("c1", "Julian"));
        order.addItem(new ItemOrder(
                new Product("p1", "Laptop", new BigDecimal("1500")), 1, new BigDecimal("1500")));
        order.confirm();

        when(orderRepository.findById("o1")).thenReturn(Optional.of(order));
        doNothing().when(orderRepository).save(order);

        payOrderUseCase.execute("o1");

        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void shouldThrowWhenOrderNotFound() {
        when(orderRepository.findById("nonexistent")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> payOrderUseCase.execute("nonexistent"))
                .isInstanceOf(OrderNotFoundException.class);
    }

    @Test
    void shouldThrowWhenOrderIsNotConfirmed() {
        Order order = new Order("o2", new PremiumCustomer("c1", "Julian"));
        when(orderRepository.findById("o2")).thenReturn(Optional.of(order));

        assertThatThrownBy(() -> payOrderUseCase.execute("o2"))
                .isInstanceOf(InvalidOrderStateException.class);
    }
}