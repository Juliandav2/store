package com.tienda.app;

import com.tienda.repository.*;
import com.tienda.service.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public OrderRepository orderRepository(JpaOrderRepository jpa) {
        return new JpaOrderRepositoryAdapter(jpa);
    }

    @Bean
    public OrderService orderService(OrderRepository repository, JpaOrderHistoryRepository orderHistoryRepository) {
        return new OrderService(repository, orderHistoryRepository);
    }
}
