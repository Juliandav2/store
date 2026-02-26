package com.tienda.application;

import com.tienda.dto.CreateOrderRequest;

import com.tienda.model.*;
import com.tienda.repository.InMemoryOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreateOrderUseCaseTest {

    private CreateOrderUseCase useCase;
    private InMemoryOrderRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryOrderRepository();
        useCase = new CreateOrderUseCase(repository);
    }

    @Test
    void shouldCreateRegularOrderSuccessfully() {
        CreateOrderRequest request = new CreateOrderRequest("1", "Julian", "REGULAR");
        Order order = useCase.execute(request);

        assertNotNull(order.getId());
        assertEquals(Order.OrderState.CREATED, order.getState());
        assertTrue(order.getCustomer() instanceof RegularCustomer);
    }

    @Test
    void shouldCreatePremiumOrderSuccessfully() {
        CreateOrderRequest request = new CreateOrderRequest("1", "Julian", "PREMIUM");
        Order order = useCase.execute(request);

        assertTrue(order.getCustomer() instanceof PremiumCustomer);
    }

    @Test
    void shouldPersistOrderInRepository() {
        CreateOrderRequest request = new CreateOrderRequest("1", "Julian", "REGULAR");
        Order order = useCase.execute(request);

        assertTrue(repository.findById(order.getId()).isPresent());
    }

    @Test
    void shouldThrowWhenRequestIsNull() {
        assertThrows(IllegalArgumentException.class, () -> useCase.execute(null));
    }

    @Test
    void shouldThrowWhenCustomerTypeIsUnknown() {
        CreateOrderRequest request = new CreateOrderRequest("1", "Julian", "VIP");
        assertThrows(IllegalArgumentException.class, () -> useCase.execute(request));
    }

    @Test
    void shouldGenerateUniqueIdsForEachOrder() {
        CreateOrderRequest request1 = new CreateOrderRequest("1", "Julian", "REGULAR");
        CreateOrderRequest request2 = new CreateOrderRequest("2", "Andrea", "REGULAR");

        Order order1 = useCase.execute(request1);
        Order order2 = useCase.execute(request2);

        // Dos órdenes nunca deben tener el mismo id
        assertNotEquals(order1.getId(), order2.getId());
    }
}