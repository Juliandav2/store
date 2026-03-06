package com.tienda.controller;

import com.tienda.dto.CreateProductRequest;
import com.tienda.dto.ProductResponse;
import com.tienda.model.Product;
import com.tienda.repository.JpaProductRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping ("/products")
public class ProductController {

    private final JpaProductRepository productRepository;

    public ProductController (JpaProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public ResponseEntity <List<ProductResponse>> getAll () {
        return ResponseEntity.ok(productRepository.findAll().stream().map(ProductResponse::new).toList());
    }

    @GetMapping ("/{id}")
    public ResponseEntity <ProductResponse> getById (@PathVariable String id) {
        return productRepository.findById(id).map(ProductResponse::new).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create (@Valid @RequestBody CreateProductRequest request) {
        Product product = new Product(UUID.randomUUID().toString(), request.getName(), request.getPrice());
        return ResponseEntity.status(201).body(new ProductResponse(productRepository.save(product)));
    }

    @PutMapping ("/{id}/price")
    public ResponseEntity <ProductResponse> updatePrice (@PathVariable String id, @Valid @RequestBody CreateProductRequest request) {
        return productRepository.findById(id).map(product -> {product.updatePrice(request.getPrice());
            return ResponseEntity.ok(new ProductResponse(productRepository.save(product)));
        }).orElse(ResponseEntity.notFound().build());
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
