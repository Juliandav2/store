package com.tienda.dto;
import java.math.BigDecimal;

public class OrderResponse {

    private final String id;
    private final String state;
    private final BigDecimal total;


    public OrderResponse (String id, String state, BigDecimal total) {

        this.id = id;
        this.state = state;
        this.total = total;

    }

    public String getId () {
        return id;
    }

    public String getState () {
        return state;
    }

    public BigDecimal getTotal () {
        return total;
    }
 }
