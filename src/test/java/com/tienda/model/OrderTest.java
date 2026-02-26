package com.tienda.model;

import com.tienda.exception.EmptyOrderException;
import com.tienda.exception.InvalidOrderStateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private Order order;
    private ItemOrder item;

    @BeforeEach
    void setUp() {
        Customer customer = new RegularCustomer("1", "Julian");
        order = new Order("1", customer);

        Product product = new Product("1", "PC Gamer", new BigDecimal("1000"));
        item = new ItemOrder(product, 1, product.getPrice());
    }

    // ─── confirm() ────────────────────────────────────────────

    @Test
    void shouldThrowWhenConfirmingEmptyOrder() {
        assertThrows(EmptyOrderException.class, order::confirm);
    }

    @Test
    void shouldConfirmOrderWithItems() {
        order.addItem(item);
        order.confirm();
        assertEquals(Order.OrderState.CONFIRMED, order.getState());
    }

    @Test
    void shouldThrowWhenConfirmingAlreadyConfirmedOrder() {
        order.addItem(item);
        order.confirm();
        assertThrows(InvalidOrderStateException.class, order::confirm);
    }

    // ─── pay() ────────────────────────────────────────────────

    @Test
    void shouldPayConfirmedOrder() {
        order.addItem(item);
        order.confirm();
        order.pay();
        assertEquals(Order.OrderState.PAID, order.getState());
    }

    @Test
    void shouldThrowWhenPayingUnconfirmedOrder() {
        order.addItem(item);
        assertThrows(InvalidOrderStateException.class, order::pay);
    }

    // ─── cancel() ─────────────────────────────────────────────

    @Test
    void shouldCancelOrderInCreatedState() {
        order.cancel();
        assertEquals(Order.OrderState.CANCELED, order.getState());
    }

    @Test
    void shouldCancelOrderInConfirmedState() {
        order.addItem(item);
        order.confirm();
        order.cancel();
        assertEquals(Order.OrderState.CANCELED, order.getState());
    }

    @Test
    void shouldThrowWhenCancelingPaidOrder() {
        order.addItem(item);
        order.confirm();
        order.pay();
        assertThrows(InvalidOrderStateException.class, order::cancel);
    }

    // ─── refund() ─────────────────────────────────────────────

    @Test
    void shouldRefundPaidOrder() {
        order.addItem(item);
        order.confirm();
        order.pay();
        order.refund();
        assertEquals(Order.OrderState.CANCELED, order.getState());
    }

    @Test
    void shouldThrowWhenRefundingCreatedOrder() {
        assertThrows(InvalidOrderStateException.class, order::refund);
    }

    @Test
    void shouldThrowWhenRefundingConfirmedOrder() {
        order.addItem(item);
        order.confirm();
        assertThrows(InvalidOrderStateException.class, order::refund);
    }

    // ─── isRefundable() ───────────────────────────────────────

    @Test
    void shouldBeRefundableWhenPaid() {
        order.addItem(item);
        order.confirm();
        order.pay();
        assertTrue(order.isRefundable());
    }

    @Test
    void shouldNotBeRefundableWhenCreated() {
        assertFalse(order.isRefundable());
    }

    @Test
    void shouldNotBeRefundableWhenConfirmed() {
        order.addItem(item);
        order.confirm();
        assertFalse(order.isRefundable());
    }
}