package com.tienda.application;
import com.tienda.exepcion.OrderNotFoundException;
import com.tienda.model.*;
import com.tienda.repository.OrderRepository;

public class AddProductOrderUserCase {

    private final OrderRepository repository;

    public AddProductOrderUserCase (OrderRepository repository) {
        this.repository = repository;

    }

    public void execute (String orderId, Product product, int quantity) {

        if (product == null) {
            throw new IllegalArgumentException("Product cannot be negative");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        Order order = repository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
        ItemOrder item = new ItemOrder(product, quantity, product.getPrice());
        order.addItem(item);
    }
}
