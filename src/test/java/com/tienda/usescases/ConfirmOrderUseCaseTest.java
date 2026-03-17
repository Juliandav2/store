package com.tienda.usescases;

import com.tienda.model.PremiumCustomer;
import com.tienda.model.ItemOrder;
import com.tienda.model.Product;
import com.tienda.application.ConfirmOrderUseCase;
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
public class ConfirmOrderUseCaseTest {

    @Mock
    private OrderRepository orderRepository;

    private ConfirmOrderUseCase confirmOrderUseCase;

    @BeforeEach
    void setUp() {
        confirmOrderUseCase = new ConfirmOrderUseCase(orderRepository);
    }

    @Test
    void shouldConfirmOrderSuccessfully() {
        Order order = new Order("o1", new PremiumCustomer("c1", "Julian"));
        order.addItem(new ItemOrder(
                new Product("p1", "Laptop", new BigDecimal("1500")), 1, new BigDecimal("1500")));

        when(orderRepository.findById("o1")).thenReturn(Optional.of(order));
        doNothing().when(orderRepository).save(order);

        confirmOrderUseCase.execute("o1");

        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void shouldThrowWhenOrderNotFound() {
        when(orderRepository.findById("nonexistent")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> confirmOrderUseCase.execute("nonexistent"))
                .isInstanceOf(OrderNotFoundException.class);
    }

    @Test
    void shouldThrowWhenOrderIsEmpty() {
        Order order = new Order("o2", new PremiumCustomer("c1", "Julian"));
        when(orderRepository.findById("o2")).thenReturn(Optional.of(order));

        assertThatThrownBy(() -> confirmOrderUseCase.execute("o2"))
                .isInstanceOf(Exception.class);
    }
}
