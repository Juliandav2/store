package com.tienda.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.PrimitiveIterator;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void DoNotConfirmAnEmptyOrder () {
        Customer customer = new RegularCustomer("1", "Juan");
        Order order = new Order("1", customer);

        assertThrows(IllegalStateException.class, order::Confirm);
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


}