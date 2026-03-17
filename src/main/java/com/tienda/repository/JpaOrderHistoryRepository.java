package com.tienda.repository;

import com.tienda.model.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaOrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
    List<OrderHistory> findByOrderIdOrderByChangedAtAsc (String orderId);
}

