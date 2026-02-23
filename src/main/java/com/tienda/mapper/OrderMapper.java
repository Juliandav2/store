package com.tienda.mapper;
import com.tienda.dto.OrderResponse;
import com.tienda.model.Order;

public class OrderMapper {

    private OrderMapper () {
    }

    public static OrderResponse toResponse (Order order) {

        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        return new OrderResponse(order.getId(), order.getState().name(), order.getTotal());
    }

}
