package com.tienda.controller;

import com.tienda.dto.*;
import com.tienda.mapper.OrderMapper;
import com.tienda.model.*;
import com.tienda.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tienda.service.OrderService;
import java.util.List;

/**
 * REST controller responsible for handling order-related HTTP requests.
 *
 * <p>
 * Exposes order operations as REST endpoints.
 * Delegates all business logic to {@link OrderService}.
 * </p>
 *
 * <p>Layer: Interface / Presentation</p>
 */

@RestController
@RequestMapping ("/orders")
public class OrderController {

    private final OrderService service;

    /**
     * Spring injects OrderService automatically via constructor injection.
     *
     * @param service service responsible for order orchestration
     */

    public OrderController(OrderService service) {
        this.service = service;
    }

    /**
     * Creates a new order
     *
     * POST /orders
     *
     * @param request request body with customer data
     * @return 201 CREATED with the order response
     */

    @PostMapping
    public ResponseEntity <OrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
        Order order = service.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderMapper.toResponse(order));

    }

    /**
     * Adds a product to an existing order.
     *
     * POST /orders/products
     *
     * @param request request body with product and order data
     * @return 200 OK
     */

    @PostMapping("/products")
    public ResponseEntity<Void> addProduct (@RequestBody AddProductRequest request) {
        service.addProduct(request.getOrderId(),new Product(request.getProductId(), request.getProductName(), request.getPrice()),request.getQuantity());
        return ResponseEntity.ok().build();
    }

    /**
     * Confirms an existing order.
     *
     * PATCH /orders/{orderId}/confirm
     *
     * @param orderId path variable with order identifier
     * @return 200 OK
     */

    @PatchMapping("/{orderId}/confirm")
    public ResponseEntity<Void> confirm (@PathVariable String orderId) {
        service.confirm(orderId);
        return ResponseEntity.ok().build();
    }

    /**
     * Processes payment for an existing order.
     *
     * PATCH /orders/{orderId}/pay
     *
     * @param orderId path variable with order identifier
     * @return 200 OK
     */

    @PatchMapping("/{orderId}/pay")
    public ResponseEntity<Void> pay (@PathVariable String orderId) {
        service.pay(orderId);
        return ResponseEntity.ok().build();
    }

    /**
     * Cancels an existing order.
     *
     * PATCH /orders/{orderId}/cancel
     *
     * @param orderId path variable with order identifier
     * @return 200 OK
     */

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancel (@PathVariable String orderId) {
        service.cancel(orderId);
        return ResponseEntity.ok().build();
    }

    /**
     * Processes a refund for an existing order.
     *
     * PATCH /orders/{orderId}/refund
     *
     * @param orderId path variable with order identifier
     * @return 200 OK
     */

    @PatchMapping("/{orderId}/refund")
    public ResponseEntity<Void> refund (@PathVariable String orderId) {
        service.refund(orderId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity <org.springframework.data.domain.Page<Order>> getOrders (@RequestParam (defaultValue = "0") int page, @RequestParam (defaultValue = "10") int size) {
        return ResponseEntity.ok(service.getOrders(page, size));
    }
}