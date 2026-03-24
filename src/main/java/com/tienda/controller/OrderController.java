package com.tienda.controller;

import com.tienda.dto.*;
import com.tienda.mapper.OrderMapper;
import com.tienda.model.*;
import com.tienda.service.EmailService;
import com.tienda.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

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

@Tag(name = "Orders", description = "Order management endpoints")
@RestController
@RequestMapping ("/orders")
public class OrderController {

    private final OrderService service;
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OrderController.class);
    private final EmailService emailService;

    /**
     * Spring injects OrderService automatically via constructor injection.
     *
     * @param service service responsible for order orchestration
     */

    public OrderController(OrderService service, EmailService emailService) {
        this.service = service;
        this.emailService = emailService;
    }

    /**
     * Creates a new order
     *
     * POST /orders
     *
     * @param request request body with customer data
     * @return 201 CREATED with the order response
     */

    @Operation(summary = "Create order")
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

    @Operation(summary = "Add product to order")
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

    @Operation(summary = "Confirm order", description = "Order must have at least one item")
    @PatchMapping("/{orderId}/confirm")
    public ResponseEntity<Void> confirm (@PathVariable String orderId) {
        service.confirm(orderId);
        log.info("Sending email to: {}", getCurrentUsername());
        emailService.sendOrderStatusEmail(getCurrentUsername(), orderId, "CONFIRMED");
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

    @Operation(summary = "Pay order", description = "Order must be confirmed first")
    @PatchMapping("/{orderId}/pay")
    public ResponseEntity<Void> pay (@PathVariable String orderId) {
        service.pay(orderId);
        try {
            emailService.sendOrderStatusEmail(getCurrentUsername(), orderId, "PAID");
        } catch (Exception e) {
            log.warn("Could not send email: {}", e.getMessage());
        }
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

    @Operation(summary = "Cancel order")
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancel (@PathVariable String orderId) {
        service.cancel(orderId);
        try {
            emailService.sendOrderStatusEmail(getCurrentUsername(), orderId, "CANCELLED");
        } catch (Exception e) {
            log.warn("Could not send email: {}", e.getMessage());
        };
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

    @Operation(summary = "Refund order", description = "Order must be paid first")
    @PatchMapping("/{orderId}/refund")
    public ResponseEntity<Void> refund (@PathVariable String orderId) {
        try {
            emailService.sendOrderStatusEmail(getCurrentUsername(), orderId, "CONFIRMED");
        } catch (Exception e) {
            log.warn("Could not send email: {}", e.getMessage());
        }
        service.refund(orderId);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get all orders", description = "Returns paginated list of orders with optional filters by state and customerId")
    @GetMapping
    public ResponseEntity <org.springframework.data.domain.Page<Order>> getOrders (
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size,
            @RequestParam (required = false) String state,
            @RequestParam (required = false) String customerId) {
        log.info("GET /orders - page={}, size={}, state={}, customerId={}",
                page, size, state, customerId);
        return ResponseEntity.ok(service.getOrders(page, size, state, customerId));
    }

    @Operation(summary = "Get order history", description = "Returns all state changes with timestamps")
    @GetMapping("/{orderId}/history")
    public ResponseEntity<List<OrderHistoryResponse>> getHistory (@PathVariable String orderId) {
        log.info("GET /orders/{}/history", orderId);
        return ResponseEntity.ok(service.getOrderHistory(orderId));
    }

    private String getCurrentUsername() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return auth != null ? auth.getName() : "anonymous";
        } catch (Exception e) {
            return "anonymous";
        }
    }
}