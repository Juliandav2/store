package com.tienda.controller;

import com.tienda.dto.CreateProductRequest;
import com.tienda.dto.ProductResponse;
import com.tienda.model.Product;
import com.tienda.repository.JpaProductRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping ("/products")
public class ProductController {

    private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ProductController.class);
    private final JpaProductRepository productRepository;

    public ProductController (JpaProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public ResponseEntity<org.springframework.data.domain.Page<ProductResponse>> getAll (@RequestParam (defaultValue = "0") int page, @RequestParam (defaultValue = "10") int size) {
        log.info("GET /products - pages={}, size={}", page, size);
        return ResponseEntity.ok(productRepository.findAll(PageRequest.of(page, size)).map(ProductResponse::new));
    }

    @GetMapping ("/{id}")
    public ResponseEntity <ProductResponse> getById (@PathVariable String id) {
        log.info("GET /products/{}", id);
        return productRepository.findById(id).map(ProductResponse::new).map(ResponseEntity::ok).orElseGet(() -> {log.warn("Product not found: {}", id);
            return ResponseEntity.notFound().build();
        });
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create (@Valid @RequestBody CreateProductRequest request) {
        log.info("POST /products - name={}", request.getName());
        Product product = new Product(UUID.randomUUID().toString(), request.getName(), request.getPrice());
        ProductResponse response = new ProductResponse(productRepository.save(product));
        log.info("Product created: {}", response.getId());
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping ("/{id}/price")
    public ResponseEntity <ProductResponse> updatePrice (@PathVariable String id, @Valid @RequestBody CreateProductRequest request) {
        log.info("PUT /products/{}/price - price={}", id, request.getPrice());
        return productRepository.findById(id).map(product -> {product.updatePrice(request.getPrice());
            return ResponseEntity.ok(new ProductResponse(productRepository.save(product)));
        }).orElseGet(() -> {log.warn("Product not found for update: {}", id);
            return ResponseEntity.notFound().build();
        });
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity <Void> delete (@PathVariable String id) {
        log.info("DELETE /products/{}", id);
        if (!productRepository.existsById(id)) {
            log.warn("Product not found for delete: {}", id);
            return ResponseEntity.notFound().build();
        }

        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
