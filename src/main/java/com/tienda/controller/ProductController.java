package com.tienda.controller;

import com.tienda.model.Product;
import com.tienda.repository.JpaProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping ("/products")
public class ProductController {

    private final JpaProductRepository productRepository;

    public ProductController (JpaProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public ResponseEntity <List<Product>> getAll () {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @GetMapping ("/{id}")
    public ResponseEntity <Product> getById (@PathVariable String id) {
        return productRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

     @PostMapping
    public ResponseEntity<Product> create (@RequestBody Map<String, String> body) {
        Product product = new Product(UUID.randomUUID().toString(), body.get("name"), new BigDecimal(body.get("price")));
        return ResponseEntity.status(201).body(productRepository.save(product));
    }

    @PutMapping ("/{id}/price")
    public ResponseEntity <Product> updatePrice (@PathVariable String id, @RequestBody Map <String, String> body) {
        return productRepository.findById(id).map(product -> {product.updatePrice(new BigDecimal(body.get("price")));
            return ResponseEntity.ok(productRepository.save(product));}).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity <Void> delete (@PathVariable String id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
