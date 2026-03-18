package com.tienda.controller;

import com.tienda.dto.CreateProductRequest;
import com.tienda.dto.ProductResponse;
import com.tienda.model.Product;
import com.tienda.repository.JpaProductRepository;
import com.tienda.service.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.cache.annotation.CacheEvict;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping ("/products")
public class ProductController {

    private final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final JpaProductRepository productRepository;
    private final ProductService productService;

    public ProductController (JpaProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll (@RequestParam (defaultValue = "0") int page,
                                                         @RequestParam (defaultValue = "10") int size,
                                                         @RequestParam (required = false) String name,
                                                         @RequestParam (required = false) BigDecimal minPrice,
                                                         @RequestParam (required = false) BigDecimal maxPrice) {

        log.info("GET /products - pages={}, size={}, name={}, minPrice={}, maxPrice={}", page, size, name, minPrice, maxPrice);
        return ResponseEntity.ok(productService.getAll(page, size, name, minPrice, maxPrice));
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
        productService.evictCache();
        log.info("Product created: {}", response.getId());
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping ("/{id}/price")
    public ResponseEntity <ProductResponse> updatePrice (@PathVariable String id, @Valid @RequestBody CreateProductRequest request) {
        log.info("PUT /products/{}/price - price={}", id, request.getPrice());

        return productRepository.findById(id).map(product -> {product.updatePrice(request.getPrice());
            productService.evictCache();
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
        productService.evictCache();
        return ResponseEntity.noContent().build();
    }
}
