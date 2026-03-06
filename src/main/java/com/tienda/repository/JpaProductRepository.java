package com.tienda.repository;

import com.tienda.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends JpaRepository <Product, String> {}
