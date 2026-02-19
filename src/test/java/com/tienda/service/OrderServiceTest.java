package com.tienda.service;
import com.tienda.model.*;
import com.tienda.repository.InMemoryRepository;
import com.tienda.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    @Test
    void shouldCreateConfirmAndPayOrderSuccessfully () {

        OrderRepository repository = new InMemoryRepository();
        OrderService service = new OrderService(repository);
        Customer customer = new RegularCustomer("1", "Julian");
        Product product = new Product("1", "PC gamer", new BigDecimal("5600000"));
        Order order = service.createOrder(customer);

        service.addProduct(order.getId(),product,1);
        service.confirmOrder(order.getId());
        service.paidOrder(order.getId());

        assertEquals(Order.OrderState.PAID, order.getState());
    }

    @Test
    void shouldThrowExceptionWhenOrderNotFound () {

        OrderRepository repository = new InMemoryRepository();
        OrderService service = new OrderService(repository);

        assertThrows(java.util.NoSuchElementException.class, () -> service.confirmOrder("Invalid - ID"));

    }

    @Test
    void shouldThrowExceptionWhenProductIsNull () {
        OrderService service = new OrderService(new InMemoryRepository());
        Customer customer = new RegularCustomer("1", "Julian");
        Order order = service.createOrder(customer);

        assertThrows(IllegalArgumentException.class,() -> service.addProduct(order.getId(), null, 1));
    }

    @Test
    void shouldThrowExceptionWhenQuantityIsInvalid () {
        OrderService service = new OrderService(new InMemoryRepository());
        Customer customer = new RegularCustomer("1", "Julian");
        Product product = new Product("1", "PC Gamer", new BigDecimal(5000000));
        Order order = service.createOrder(customer);

        assertThrows(IllegalArgumentException.class, () -> service.addProduct(order.getId(), product, 0));
    }

    @Test
    void shouldThrowExceptionWhenOrderIdIsInvalid () {
        OrderService service = new OrderService(new InMemoryRepository());
        Product product = new Product("1", "PC gamer", new BigDecimal(5000000));

        assertThrows(IllegalArgumentException.class, () -> service.addProduct("", product, 1));
    }
}
