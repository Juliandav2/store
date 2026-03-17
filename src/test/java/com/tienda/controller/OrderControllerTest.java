package com.tienda.controller;

import com.tienda.dto.*;
import com.tienda.exception.OrderNotFoundException;
import com.tienda.repository.InMemoryOrderRepository;
import com.tienda.repository.JpaOrderHistoryRepository;
import com.tienda.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;


import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class OrderControllerTest {

    @Mock
    private JpaOrderHistoryRepository orderHistoryRepository;
    private OrderController controller;
    private String orderId;

    @BeforeEach
    void setUp() {
        controller = new OrderController(new OrderService(new InMemoryOrderRepository(), orderHistoryRepository));
        OrderResponse response = controller. createOrder(new  CreateOrderRequest("1", "Julian", "REGULAR")).getBody();
        orderId = response.getId();
    }

    // ─── createOrder() ────────────────────────────────────────

    @Test
    void shouldCreateOrderAndReturnResponse() {
        OrderResponse response = controller.createOrder(new CreateOrderRequest("2", "Andrea", "PREMIUM")).getBody();
        assertNotNull(response.getId());
        assertEquals("CREATED", response.getState());
    }

    @Test
    void shouldThrowWhenCreateRequestIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> controller.createOrder(null));
    }

    // ─── addProduct() ─────────────────────────────────────────

    @Test
    void shouldAddProductToOrder() {
        assertDoesNotThrow(() -> controller.addProduct(
                new AddProductRequest(orderId, "p1", "Mouse", new BigDecimal("50"), 1)
        ));
    }

    @Test
    void shouldThrowWhenAddProductRequestIsNull() {
        assertThrows(NullPointerException.class,
                () -> controller.addProduct(null));
    }

    // ─── confirm() ────────────────────────────────────────────

    @Test
    void shouldConfirmOrder() {
        controller.addProduct(
                new AddProductRequest(orderId, "p1", "Mouse", new BigDecimal("50"), 1)
        );
        assertDoesNotThrow(() -> controller.confirm(orderId));
    }

    @Test
    void shouldThrowWhenConfirmOrderIdIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> controller.confirm(null));
    }

    // ─── cancel() ─────────────────────────────────────────────

    @Test
    void shouldCancelCreatedOrder() {
        assertDoesNotThrow(() -> controller.cancel(orderId));
    }

    @Test
    void shouldThrowWhenCancelOrderIdIsBlank() {
        assertThrows(IllegalArgumentException.class,
                () -> controller.cancel("  "));
    }

    // ─── refund() ─────────────────────────────────────────────

    @Test
    void shouldRefundPaidOrder() {
        controller.addProduct(
                new AddProductRequest(orderId, "p1", "Mouse", new BigDecimal("50"), 1)
        );
        controller.confirm(orderId);
        controller.pay(orderId);
        assertDoesNotThrow(() -> controller.refund(orderId));
    }

    @Test
    void shouldThrowWhenRefundOrderIdIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> controller.refund(null));
    }

    // ─── order not found ──────────────────────────────────────

    @Test
    void shouldThrowWhenOrderNotFound() {
        assertThrows(OrderNotFoundException.class,
                () -> controller.confirm("id-que-no-existe"));
    }
}
