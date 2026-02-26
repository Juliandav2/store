package com.tienda;

import com.tienda.controller.OrderController;
import com.tienda.dto.*;
import com.tienda.repository.InMemoryOrderRepository;
import com.tienda.service.OrderService;

import java.math.BigDecimal;

/**
 * Entry point of the Store application.
 *
 * <p>
 * This class demonstrates the full order lifecycle:
 * create → add products → confirm → pay → refund.
 * </p>
 *
 * <p>
 * Currently uses in-memory persistence.
 * Intended to be replaced by a Spring Boot entry point
 * in future iterations.
 * </p>
 */

public class Main {
    public static void main(String[] args) {

        OrderController controller = new OrderController(new OrderService(new InMemoryOrderRepository()));

        // Create order

        CreateOrderRequest createRequest = new CreateOrderRequest("customer-1", "Julian", "PREMIUM");
        var orderResponse = controller.createOrder(createRequest);
        String orderId = orderResponse.getId();

        System.out.println("Order created:         " + orderId);
        System.out.println("State:                 " + orderResponse.getState());
        System.out.println("Total:                 " + orderResponse.getTotal());

        // Add products

        controller.addProduct(new AddProductRequest(orderId, "product-1", "PC gamer", new BigDecimal("2000000"), 1));
        controller.addProduct(new AddProductRequest(orderId, "product-2", "Mouse Gamer", new BigDecimal("700000"), 1));

        System.out.println("\nProducts added.");

        // Confirm

        controller.confirm(orderId);
        System.out.println("Order confirmed");

        // Pay

        controller.pay(orderId);
        System.out.println("Order paid");
        System.out.println("Total with 10% premium discount: " + controller.createOrder(new CreateOrderRequest("x", "x", "PREMIUM")).getTotal());

        // Refund

        controller.refund(orderId);
        System.out.println("Order refunded");
        System.out.println("\nFinal state: CANCELED (refunded)");
    }
}