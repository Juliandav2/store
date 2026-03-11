package com.tienda.repository;

import com.tienda.model.Order;
import com.tienda.model.Order.OrderState;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecification {

    public static Specification<Order> hasState (String state) {
        return (root,query, cb) -> state == null ? null :
                cb.equal(root.get("state"), OrderState.valueOf(state.toUpperCase()));
    }

    public static Specification<Order> hasCustomerId (String customerId) {
        return (root, query, cb) -> customerId == null ? null :
                cb.equal(root.get("customer").get("id"), customerId);
    }
}
