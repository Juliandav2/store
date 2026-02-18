package com.tienda.service;
import com.tienda.model.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    @Test
    void shouldCreateConfirmAndPayOrderSuccessfully () {

        OrderService service = new OrderService();
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

        OrderService service = new OrderService();
        assertThrows(java.util.NoSuchElementException.class, () -> service.confirmOrder("Invalid - ID"));

    }
}
