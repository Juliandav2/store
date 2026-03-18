package com.tienda.service;

import com.tienda.dto.ProductResponse;
import com.tienda.repository.JpaProductRepository;
import com.tienda.repository.ProductSpecification;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.tienda.model.Product;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    private final JpaProductRepository productRepository;

    public ProductService (JpaProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Cacheable (value = "products", key = "#page + '-' + #size + '-' + #name + '-' + #minPrice + '-' + #maxPrice")
    public List<ProductResponse> getAll (int page, int size, String name, BigDecimal minPrice, BigDecimal maxPrice) {
        Specification<Product> specification = Specification
                .where(ProductSpecification.hasName(name))
                .and(ProductSpecification.hasMinPrice(minPrice))
                .and(ProductSpecification.hasMaxPrice(maxPrice));
        return productRepository
                .findAll(specification, PageRequest.of(page, size))
                .map(ProductResponse::new).getContent();
    }

    @CacheEvict (value = "products", allEntries = true)
    public void evictCache () {}
}
