package com.tienda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Entry point of the Store application.
 *
 * <p>
 * Bootstraps the Spring Boot context and starts
 * the embedded Tomcat server.
 * </p>
 */

@SpringBootApplication
@EnableCaching
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }
}