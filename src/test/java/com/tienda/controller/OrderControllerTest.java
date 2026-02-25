package com.tienda.controller;
import com.tienda.dto.CreateOrderRequest;
import com.tienda.dto.OrderResponse;
import com.tienda.repository.InMemoryOrderRepository;
import com.tienda.service.OrderService;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class OrderControllerTest {

    @Test
    void shouldCreateOrderThroughController () {
        InMemoryOrderRepository repository = new InMemoryOrderRepository();
        OrderService service = new OrderService(repository);
        OrderController controller = new OrderController(service);

        CreateOrderRequest request = new CreateOrderRequest("1", "Julian");
        OrderResponse response = controller.createOrder(request);

        assertNotNull(response);
        assertEquals("CREATED", response.getState());
        assertEquals(BigDecimal.ZERO, response.getTotal());
    }

}
