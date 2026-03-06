package com.tienda.controller;

import com.tienda.model.Customer;
import com.tienda.model.PremiumCustomer;
import com.tienda.model.RegularCustomer;
import com.tienda.repository.JpaCustomerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping ("/customers")
public class CustomerController {

    private final JpaCustomerRepository customerRepository;

    public CustomerController (JpaCustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public ResponseEntity <List<Customer>> getAll () {
        return ResponseEntity.ok(customerRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity <Customer> getById (@PathVariable String id) {
        return customerRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity <Customer> create (@RequestBody Map<String, String> body) {
        String type = body.get("type");
        String id = UUID.randomUUID().toString();
        String name = body.get("name");

        Customer customer = switch (type.toUpperCase()) {
            case "PREMIUM" -> new PremiumCustomer(id, name);
            case "REGULAR" -> new RegularCustomer(id, name);
            default -> throw new IllegalArgumentException("Invalid customer type: " + type);
        };

        return ResponseEntity.status(201).body(customerRepository.save(customer));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity <Void> delete (@PathVariable String id) {
        if (!customerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        customerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
