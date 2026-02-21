package com.tienda.application;
import com.tienda.model.Customer;
import com.tienda.model.Order;
import com.tienda.repository.OrderRepository;
import java.util.UUID;

public class CreateOrderUseCase {

    private final OrderRepository repository;

    public CreateOrderUseCase (OrderRepository repository) {
        this.repository = repository;
    }

    public Order execute (Customer customer) {

        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        Order order = new Order(UUID.randomUUID().toString(), customer);
        repository.save(order);

        return order;
    }
}
