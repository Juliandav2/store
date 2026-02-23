package com.tienda.application;
import com.tienda.exepcion.OrderNotFoundException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.tienda.repository.*;
import com.tienda.model.*;
import java.util.*;
import com.tienda.repository.InMemoryRepository;


public class ConfirmOrderUseCaseTest {

    @Test
    void shouldThrowWhenOrderDoesNotExist() {

        OrderRepository repository = new InMemoryRepository();
        ConfirmOrderUserCase confirm = new ConfirmOrderUserCase(repository);

        assertThrows(OrderNotFoundException.class, () ->
                confirm.execute("non-existent-id")
        );
    }
}
