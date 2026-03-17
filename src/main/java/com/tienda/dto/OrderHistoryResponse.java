package com.tienda.dto;

import com.tienda.model.OrderHistory;
import java.time.LocalDateTime;

public class OrderHistoryResponse {

    private final String state;
    private final LocalDateTime changedAt;

    public OrderHistoryResponse (OrderHistory history) {
        this.state = history.getState();
        this.changedAt = history.getChangedAt();
    }

    public String getState() {
        return state;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }
}
