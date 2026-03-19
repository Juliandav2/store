package com.tienda.service;

import com.tienda.FakeOrderHistoryRepository;
import com.tienda.dto.*;
import com.tienda.exception.*;
import com.tienda.model.*;
import com.tienda.repository.InMemoryOrderRepository;
import com.tienda.repository.JpaOrderHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;


class OrderServiceTest {


    private JpaOrderHistoryRepository orderHistoryRepository;
    private OrderService service;
    private InMemoryOrderRepository repository;
    private Product product;

    @BeforeEach
    void setUp() {
        orderHistoryRepository = new FakeOrderHistoryRepository();
        repository = new InMemoryOrderRepository();
        service = new OrderService(repository, orderHistoryRepository);
        product = new Product("p1", "PC Gamer", new BigDecimal("1000"));
    }

    // ─── createOrder() ────────────────────────────────────────

    @Test
    void shouldCreateRegularOrderSuccessfully() {
        CreateOrderRequest request = new CreateOrderRequest("1", "Julian", "REGULAR");
        Order order = service.createOrder(request);

        assertNotNull(order.getId());
        assertEquals(Order.OrderState.CREATED, order.getState());
        assertTrue(order.getCustomer() instanceof RegularCustomer);
    }

    @Test
    void shouldCreatePremiumOrderSuccessfully() {
        CreateOrderRequest request = new CreateOrderRequest("1", "Julian", "PREMIUM");
        Order order = service.createOrder(request);

        assertTrue(order.getCustomer() instanceof PremiumCustomer);
    }

    @Test
    void shouldThrowWhenCustomerTypeIsInvalid() {
        CreateOrderRequest request = new CreateOrderRequest("1", "Julian", "VIP");
        assertThrows(IllegalArgumentException.class, () -> service.createOrder(request));
    }

    // ─── addProduct() ─────────────────────────────────────────

    @Test
    void shouldAddProductToExistingOrder() {
        Order order = service.createOrder(new CreateOrderRequest("1", "Julian", "REGULAR"));

        service.addProduct(order.getId(), product, 2);

        Order updated = repository.findById(order.getId()).get();
        assertEquals(1, updated.getItems().size());
        assertEquals(2, updated.getItems().get(0).getAmount());
    }

    @Test
    void shouldThrowWhenAddingProductToNonExistentOrder() {
        assertThrows(OrderNotFoundException.class,
                () -> service.addProduct("id-inexistente", product, 1));
    }

    // ─── confirm() ────────────────────────────────────────────

    @Test
    void shouldConfirmOrderWithItems() {
        Order order = service.createOrder(new CreateOrderRequest("1", "Julian", "REGULAR"));
        service.addProduct(order.getId(), product, 1);
        service.confirm(order.getId());

        assertEquals(Order.OrderState.CONFIRMED, repository.findById(order.getId()).get().getState());
    }

    @Test
    void shouldThrowWhenConfirmingEmptyOrder() {
        Order order = service.createOrder(new CreateOrderRequest("1", "Julian", "REGULAR"));
        assertThrows(EmptyOrderException.class, () -> service.confirm(order.getId()));
    }

    // ─── pay() ────────────────────────────────────────────────

    @Test
    void shouldPayConfirmedOrder() {
        Order order = service.createOrder(new CreateOrderRequest("1", "Julian", "REGULAR"));
        service.addProduct(order.getId(), product, 1);
        service.confirm(order.getId());
        service.pay(order.getId());

        assertEquals(Order.OrderState.PAID, repository.findById(order.getId()).get().getState());
    }

    @Test
    void shouldThrowWhenPayingUnconfirmedOrder() {
        Order order = service.createOrder(new CreateOrderRequest("1", "Julian", "REGULAR"));
        service.addProduct(order.getId(), product, 1);

        // Nunca confirmamos → debe lanzar excepción
        assertThrows(InvalidOrderStateException.class, () -> service.pay(order.getId()));
    }

    // ─── cancel() ─────────────────────────────────────────────

    @Test
    void shouldCancelCreatedOrder() {
        Order order = service.createOrder(new CreateOrderRequest("1", "Julian", "REGULAR"));
        service.cancel(order.getId());

        assertEquals(Order.OrderState.CANCELED, repository.findById(order.getId()).get().getState());
    }

    @Test
    void shouldThrowWhenCancelingPaidOrder() {
        Order order = service.createOrder(new CreateOrderRequest("1", "Julian", "REGULAR"));
        service.addProduct(order.getId(), product, 1);
        service.confirm(order.getId());
        service.pay(order.getId());

        assertThrows(InvalidOrderStateException.class, () -> service.cancel(order.getId()));
    }

    // ─── refund() ─────────────────────────────────────────────

    @Test
    void shouldRefundPaidOrder() {
        Order order = service.createOrder(new CreateOrderRequest("1", "Julian", "REGULAR"));
        service.addProduct(order.getId(), product, 1);
        service.confirm(order.getId());
        service.pay(order.getId());
        service.refund(order.getId());

        assertEquals(Order.OrderState.CANCELED, repository.findById(order.getId()).get().getState());
    }

    @Test
    void shouldThrowWhenRefundingCreatedOrder() {
        Order order = service.createOrder(new CreateOrderRequest("1", "Julian", "REGULAR"));
        assertThrows(InvalidOrderStateException.class, () -> service.refund(order.getId()));
    }

    // ─── full lifecycle ───────────────────────────────────────

    @Test
    void shouldCompleteFullOrderLifecycle() {
        Order order = service.createOrder(new CreateOrderRequest("1", "Julian", "PREMIUM"));
        String orderId = order.getId();

        service.addProduct(orderId, product, 2);
        service.confirm(orderId);
        service.pay(orderId);

        Order result = repository.findById(orderId).get();
        assertEquals(Order.OrderState.PAID, result.getState());
        // PREMIUM tiene 10% de descuento → 2000 * 0.90 = 1800.00
        assertEquals(new BigDecimal("1800.00"), result.getTotalWithDiscount());
    }

    // ─── order not found ──────────────────────────────────────

    @Test
    void shouldThrowWhenOrderNotFoundOnConfirm() {
        assertThrows(OrderNotFoundException.class, () -> service.confirm("id-inexistente"));
    }

    @Test
    void shouldThrowWhenOrderNotFoundOnPay() {
        assertThrows(OrderNotFoundException.class, () -> service.pay("id-inexistente"));
    }

    @Test
    void shouldThrowWhenOrderNotFoundOnCancel() {
        assertThrows(OrderNotFoundException.class, () -> service.cancel("id-inexistente"));
    }

    @Test
    void shouldThrowWhenOrderNotFoundOnRefund() {
        assertThrows(OrderNotFoundException.class, () -> service.refund("id-inexistente"));
    }
}