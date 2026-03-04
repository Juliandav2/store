package com.tienda.app;

import com.tienda.repository.InMemoryOrderRepository;
import com.tienda.repository.JpaOrderRepository;
import com.tienda.repository.JpaOrderRepositoryAdapter;
import com.tienda.repository.OrderRepository;
import com.tienda.service.OrderService;
import jakarta.validation.Valid;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration class.
 *
 * <p>
 * Defines the beans that Spring will manage.
 * </p>
 */

@Configuration
public class AppConfig {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String datasourceUsername;

    @Value("${spring.datasource.password}")
    private String datasourcePassword;

    /**
     * Configures and runs Flyway migrations manually.
     */

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        return Flyway.configure()
                .dataSource(datasourceUrl, datasourceUsername, datasourcePassword)
                .locations("classpath:db/migration")
                .load();
    }

    @Bean
    public OrderRepository orderRepository (JpaOrderRepository jpa) {
        return new JpaOrderRepositoryAdapter(jpa);
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
