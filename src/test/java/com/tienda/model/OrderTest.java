package com.tienda.model;

import com.tienda.exepcion.EmptyOrderException;
import com.tienda.repository.InMemoryRepository;
import com.tienda.service.OrderService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.PrimitiveIterator;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void DoNotConfirmAnEmptyOrder () {

        Order order = new Order("1", new RegularCustomer("1", "Julian"));
        assertThrows(EmptyOrderException.class, order::Confirm);
    }

    @Test
    void ConfirmOrderWithItems () {
        Customer customer = new RegularCustomer("1", "Julian");
        Order order = new Order("1", customer);

        Product product = new Product("1", "Pc Gamer", new BigDecimal("700000"));
        ItemOrder item = new ItemOrder(product, 1, product.getPrice());

        order.addItem(item);
        order.Confirm();

        assertEquals(Order.OrderState.CONFIRM,order.getState());

    }

    @Test
    void DoNotCancelPaidOrder () {
        Customer customer = new RegularCustomer("1", "Andrea");
        Order order = new Order("1", customer);

        Product product = new Product("1", "Mouse", new BigDecimal(60000));
        ItemOrder item = new ItemOrder(product, 1, product.getPrice());

        order.addItem(item);
        order.Confirm();
        order.Paid();

        assertThrows(IllegalStateException.class, order::Cancel);

    }

    @Test
    void shouldCancelOrderWhenStateIsCreated() {
        Customer customer = new RegularCustomer("1", "Julian");
        Order order = new Order("1", customer);

        assertDoesNotThrow(order::Cancel);
        assertEquals(Order.OrderState.CANCELED, order.getState());
    }

    @Test
    void shouldCancelOrderWhenStateIsConfirmed() {
        Customer customer = new RegularCustomer("1", "Julian");
        Order order = new Order("1", customer);

        Product product = new Product("1", "PC", new BigDecimal("1000"));
        order.addItem(new ItemOrder(product, 1, product.getPrice()));
        order.Confirm();

        assertDoesNotThrow(order::Cancel);
        assertEquals(Order.OrderState.CANCELED, order.getState());
    }

    @Test
    void shouldThrowExceptionWhenCancelPaidOrder() {
        Customer customer = new RegularCustomer("1", "Julian");
        Order order = new Order("1", customer);

        Product product = new Product("1", "PC", new BigDecimal("1000"));
        order.addItem(new ItemOrder(product, 1, product.getPrice()));
        order.Confirm();
        order.Paid();

        assertThrows(IllegalStateException.class, order::Cancel);
    }

    @Test
    void shouldReturnTrueWhenOrderIsPaid() {
        Customer customer = new RegularCustomer("1", "Julian");
        Order order = new Order("1", customer);

        Product product = new Product("1", "PC", new BigDecimal("1000"));
        order.addItem(new ItemOrder(product, 1, product.getPrice()));
        order.Confirm();
        order.Paid();

        assertTrue(order.isRefundable());
    }

    @Test
    void shouldReturnFalseWhenOrderIsCreated() {
        Customer customer = new RegularCustomer("1", "Julian");
        Order order = new Order("1", customer);

        assertFalse(order.isRefundable());
    }

}