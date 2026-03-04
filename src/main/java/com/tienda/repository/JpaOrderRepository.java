package com.tienda.repository;

import com.tienda.model.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;

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

public interface JpaOrderRepository extends JpaRepository <Order, String> {
}
