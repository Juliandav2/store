package com.tienda.repository;

import com.tienda.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * JPA implementation of {@link OrderRepository}.
 *
 * <p>
 * Extends JpaRepository to get CRUD operations for free.
 * Spring Data JPA generates the implementation automatically
 * at runtime — no need to write SQL or implement methods manually.
 * </p>
 *
 * <p>
 * This interface replaces {@link InMemoryOrderRepository}
 * for production use.
 * </p>
 */

public interface JpaOrderRepository extends JpaRepository <Order, String>, JpaSpecificationExecutor<Order> {}
