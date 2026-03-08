package com.tienda;

import com.tienda.model.Product;
import com.tienda.repository.JpaProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ProductRepositoryTest {

    @Autowired
    private JpaProductRepository productRepository;

    @Test
    void shouldSaveAndFindProduct() {
        Product product = new Product("p1", "Laptop", new BigDecimal("1500.00"));
        productRepository.save(product);

        Optional<Product> found = productRepository.findById("p1");

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Laptop");
        assertThat(found.get().getPrice()).isEqualByComparingTo("1500.00");
    }

    @Test
    void shouldReturnEmptyWhenProductNotFound() {
        Optional<Product> found = productRepository.findById("nonexistent");
        assertThat(found).isEmpty();
    }

    @Test
    void shouldDeleteProduct() {
        Product product = new Product("p2", "Mouse", new BigDecimal("25.00"));
        productRepository.save(product);

        productRepository.deleteById("p2");

        assertThat(productRepository.findById("p2")).isEmpty();
    }

    @Test
    void shouldUpdateProductPrice() {
        Product product = new Product("p3", "Keyboard", new BigDecimal("50.00"));
        productRepository.save(product);

        product.updatePrice(new BigDecimal("75.00"));
        productRepository.save(product);

        Optional<Product> updated = productRepository.findById("p3");
        assertThat(updated).isPresent();
        assertThat(updated.get().getPrice()).isEqualByComparingTo("75.00");
    }
}