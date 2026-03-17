package com.tienda.usescases;

import com.tienda.application.CreateOrderUseCase;
import com.tienda.dto.CreateOrderRequest;
import com.tienda.model.Order;
import com.tienda.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateOrderUseCaseTest {

    @Mock
    private OrderRepository orderRepository;

    private CreateOrderUseCase createOrderUseCase;

    @BeforeEach
    void setUp() {
        createOrderUseCase = new CreateOrderUseCase(orderRepository);
    }

    @Test
    void shouldCreateOrderSuccessfully() {
        CreateOrderRequest request = new CreateOrderRequest(
                "c1", "Julian", "PREMIUM"
        );
        doNothing().when(orderRepository).save(any(Order.class));

        Order order = createOrderUseCase.execute(request);

        assertThat(order).isNotNull();
        assertThat(order.getCustomer().getName()).isEqualTo("Julian");
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void shouldThrowExceptionWhenCustomerTypeIsInvalid() {
        CreateOrderRequest request = new CreateOrderRequest(
                "c1", "Julian", "INVALID"
        );

        assertThatThrownBy(() -> createOrderUseCase.execute(request))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
