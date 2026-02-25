package com.tienda.application;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.tienda.repository.*;
import com.tienda.model.*;

import java.math.BigDecimal;

public class ApplicationTest {

    @Test
    void shouldCreateOrderSuccessfully() {

        OrderRepository repository = new InMemoryOrderRepository();
        CreateOrderUseCase useCase = new CreateOrderUseCase(repository);

        Customer customer = new RegularCustomer("1", "Julian");

        Order order = useCase.execute(customer);

        assertNotNull(order);
        assertEquals(customer, order.getCustomer());
        assertEquals(Order.OrderState.CREATED, order.getState());
    }

    @Test
    void shouldAddProductToOrder() {

        OrderRepository repository = new InMemoryOrderRepository();
        CreateOrderUseCase create = new CreateOrderUseCase(repository);
        AddProductOrderUseCase add = new AddProductOrderUseCase(repository);

        Customer customer = new RegularCustomer("1", "Julian");
        Order order = create.execute(customer);

        Product product = new Product("1", "Mouse", new BigDecimal("50000"));

        add.execute(order.getId(), product, 2);

        Order updated = repository.findById(order.getId()).get();

        assertEquals(1, updated.getItems().size());
        assertEquals(new BigDecimal("100000"), updated.getTotal());

    }

    @Test
    void shouldThrowWhenQuantityIsInvalid() {

        OrderRepository repository = new InMemoryOrderRepository();
        CreateOrderUseCase create = new CreateOrderUseCase(repository);
        AddProductOrderUseCase add = new AddProductOrderUseCase(repository);

        Customer customer = new RegularCustomer("1", "Julian");
        Order order = create.execute(customer);

        Product product = new Product("1", "Mouse", new BigDecimal("50000"));

        assertThrows(IllegalArgumentException.class, () ->
                add.execute(order.getId(), product, 0)
        );
    }

    @Test
    void shouldConfirmOrderSuccessfully () {
        OrderRepository repository = new InMemoryOrderRepository();
        CreateOrderUseCase create = new CreateOrderUseCase(repository);
        ConfirmOrderUseCase confirm = new ConfirmOrderUseCase(repository);

        Customer customer = new RegularCustomer("1", "Julian");
        Order order = create.execute(customer);

        Product product = new Product("1", "Mouse", new BigDecimal("300000"));
        order.addItem(new ItemOrder(product, 1, product.getPrice()));

        confirm.execute(order.getId());

        assertEquals(Order.OrderState.CONFIRMED, order.getState());
    }
}