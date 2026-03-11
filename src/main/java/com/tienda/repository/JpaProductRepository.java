package com.tienda.repository;

import com.tienda.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JpaProductRepository extends JpaRepository <Product, String> , JpaSpecificationExecutor<Product> {}
