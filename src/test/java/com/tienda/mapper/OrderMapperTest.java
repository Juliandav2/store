package com.tienda.mapper;

import com.tienda.dto.OrderResponse;
import com.tienda.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class OrderMapperTest {

    private Order order;

    @BeforeEach
    void setUp() {
        Customer customer = new RegularCustomer("1", "Julian");
        order = new Order("1", customer);
    }

    @Test
    void shouldMapOrderToResponse() {
        OrderResponse response = OrderMapper.toResponse(order);

        assertEquals("1", response.getId());
        assertEquals("CREATED", response.getState());
        assertEquals(new BigDecimal("0.00"), response.getTotal());
    }

    @Test
    void shouldApplyDiscountInResponse() {
        Product product = new Product("1", "PC Gamer", new BigDecimal("1000"));
        order.addItem(new ItemOrder(product, 1, product.getPrice()));

        OrderResponse response = OrderMapper.toResponse(order);

        assertEquals(new BigDecimal("950.00"), response.getTotal());
    }

    @Test
    void shouldThrowWhenOrderIsNull() {
        assertThrows(NullPointerException.class, () -> OrderMapper.toResponse(null));
    }
}