package com.tienda.mapper;
import com.tienda.dto.OrderResponse;
import com.tienda.model.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class OrderMapperTest {

    @Test
    void shouldMapOrderToResponse () {
        Customer customer = new RegularCustomer("1", "Julian");
        Order order = new Order("order-1", customer);

        Product product = new Product("p1", "RTX 5090", new BigDecimal("50000"));
        order.addItem(new ItemOrder(product, 2, product.getPrice()));
        order.confirm();

        OrderResponse response = OrderMapper.toResponse(order);

        assertEquals("order-1", response.getId());
        assertEquals("CONFIRMED", response.getState());
        assertEquals(new BigDecimal("100000"), response.getTotal());
    }

    @Test
    void shouldThrowWhenOrderIsNull () {
        assertThrows(IllegalArgumentException.class, () -> OrderMapper.toResponse(null));
    }
}
