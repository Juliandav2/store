package com.tienda.mapper;
import com.tienda.dto.OrderResponse;
import com.tienda.model.Order;

import java.util.Objects;

/**
 * Mapper responsible for converting Order domain entities
 * into OrderResponse DTOs.
 *
 * <p>
 * This class isolates transformation logic between
 * the domain layer and the presentation layer.
 * </p>
 *
 * <p>
 * Implemented as a utility class to avoid instantiation.
 * </p>
 *
 * Layer: Infrastructure / Mapping
 * Responsibility: Domain to DTO transformation
 */

public final class OrderMapper {

    /**
     * Private constructor to prevent instantiation.
     */

    private OrderMapper () {
    }

    /**
     * Converts an Order domain entity into an OrderResponse DTO.
     *
     * <p>The total in the response reflects the price after
     * applying the customer's discount strategy.</p>
     *
     * @param order domain order entity
     * @return mapped response DTO with discounted total
     * @throws NullPointerException if order is null
     */

    public static OrderResponse toResponse (Order order) {

        Objects.requireNonNull(order, "Order cannot be null");
        return new OrderResponse(order.getId(), order.getState().name(), order.getTotalWithDiscount());
    }

}
