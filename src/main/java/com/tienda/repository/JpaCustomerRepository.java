package com.tienda.repository;

import com.tienda.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCustomerRepository extends JpaRepository <Customer, String> {}
