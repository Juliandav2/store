package com.tienda.controller;
import com.tienda.dto.*;
import com.tienda.mapper.OrderMapper;
import com.tienda.model.*;
import com.tienda.service.OrderService;

/**
 * Controller responsible for handling order-related operations.
 *
 * <p>
 * This class acts as the entry point for order workflows.
 * It receives request DTOs, converts them into domain objects,
 * delegates execution to the service layer,
 * and maps domain entities back into response DTOs.
 * </p>
 *
 * <p>
 * In a future Spring migration, this class will become a REST controller.
 * </p>
 *
 * Layer: Interface / Presentation
 * Responsibility: Coordinating requests and responses
 */
public class OrderController {

    private final OrderService service;

    /**
     * Creates a new instance of the controller.
     *
     * @param service service responsible for order orchestration
     */

    public OrderController(OrderService service) {
        this.service = service;
    }

    /**
     * Creates a new order for a regular customer.
     *
     * @param request request containing customer information
     * @return response DTO representing the created order
     * @throws IllegalArgumentException if request is null
     */

    public OrderResponse createOrder(CreateOrderRequest request) {

        if (request == null) {
            throw new IllegalArgumentException("CreateOrderRequest cannot be null");
        }

        Customer customer = new RegularCustomer(request.getCustomerId(), request.getCustomerName()
        );

        Order order = service.createOrder(request);
        return OrderMapper.toResponse(order);
    }

    /**
     * Adds a product to an existing order.
     *
     * @param request request containing product and order information
     * @throws IllegalArgumentException if request is null
     */

    public void addProduct(AddProductRequest request) {

        if (request == null) {
            throw new IllegalArgumentException("AddProductRequest cannot be null");
        }

        Product product = new Product(request.getProductId(), request.getProductName(), request.getPrice()

        );

        service.addProduct(request.getOrderId(), product, request.getQuantity()

        );
    }

    /**
     * Confirms an existing order.
     *
     * @param orderId identifier of the order
     */

    public void confirm(String orderId) {
        service.confirm(orderId);
    }

    /**
     * Processes payment for an order.
     *
     * @param orderId identifier of the order
     */

    public void pay(String orderId) {
        service.pay(orderId);
    }

    /**
     * Cancels an existing order.
     *
     * @param orderId identifier of the order
     */

    public void cancel(String orderId) {
        service.cancel(orderId);
    }
}