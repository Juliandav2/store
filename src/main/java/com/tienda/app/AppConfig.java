package com.tienda.app;

import com.tienda.repository.InMemoryOrderRepository;
import com.tienda.repository.OrderRepository;
import com.tienda.service.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration class.
 *
 * <p>
 * Defines the beans that Spring will manage.
 * When we migrate to JPA, InMemoryOrderRepository
 * will be replaced here without touching any other class.
 * </p>
 */

@Configuration
public class AppConfig {

    /**
     * Registers OrderRepository as a Spring bean.
     * Currently, uses in-memory implementation.
     *
     * @return InMemoryOrderRepository instance
     */

    @Bean
    public OrderRepository orderRepository () {
        return new InMemoryOrderRepository();
    }

    /**
     * Registers OrderService as a Spring bean
     * injecting the repository automatically.
     *
     * @param repository the order repository
     * @return OrderService instance
     */

    @Bean
    public OrderService orderService (OrderRepository repository) {
        return new OrderService(repository);
    }
}
