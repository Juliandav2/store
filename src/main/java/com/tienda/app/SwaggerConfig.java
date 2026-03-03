package com.tienda.app;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger / OpenAPI configuration.
 *
 * <p>
 * Configures the API documentation metadata shown
 * in the Swagger UI.
 * </p>
 */

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI () {
        return new OpenAPI()
                .info(new Info()
                .title("Store API")
                .version("1.0")
                .description("Order management REST API built with Java 21 and Spring Boot")
                .contact(new Contact()
                        .name("Julian")
                        .url("https://github.com/Juliandav2")));
    }
}
