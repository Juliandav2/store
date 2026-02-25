package com.tienda.application;
import com.tienda.exception.OrderNotFoundException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.tienda.repository.*;

import com.tienda.repository.InMemoryOrderRepository;


public class ConfirmOrderUseCaseTest {

    @Test
    void shouldThrowWhenOrderDoesNotExist() {

        OrderRepository repository = new InMemoryOrderRepository();
        ConfirmOrderUseCase confirm = new ConfirmOrderUseCase(repository);

        assertThrows(OrderNotFoundException.class, () ->
                confirm.execute("non-existent-id")
        );
    }
}
