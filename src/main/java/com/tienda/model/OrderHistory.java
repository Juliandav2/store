package com.tienda.model;

import  jakarta.persistence.*;

import  java.time.LocalDateTime;

@Entity
@Table (name = "order_history")
public class OrderHistory {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @Column (name = "order_id", nullable = false)
    private String orderId;

    @Column (nullable = false)
    private String state;

    @Column (name = "changed_at", nullable = false)
    private LocalDateTime changedAt;

    protected OrderHistory () {}

    public OrderHistory (String orderId, String state) {
        this.orderId = orderId;
        this.state = state;
        this.changedAt = LocalDateTime.now();
    }

    public long getId () {
        return id;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getState() {
        return state;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }
}
