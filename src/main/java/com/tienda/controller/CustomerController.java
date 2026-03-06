package com.tienda.controller;

import com.tienda.dto.CreateCustomerRequest;
import com.tienda.dto.CustomerResponse;
import com.tienda.model.Customer;
import com.tienda.model.PremiumCustomer;
import com.tienda.model.RegularCustomer;
import com.tienda.repository.JpaCustomerRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController
@RequestMapping ("/customers")
public class CustomerController {

    private final JpaCustomerRepository customerRepository;

    public CustomerController (JpaCustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public ResponseEntity <org.springframework.data.domain.Page<CustomerResponse>> getAll (@RequestParam (defaultValue = "0") int page, @RequestParam (defaultValue = "10") int size) {
        return ResponseEntity.ok(customerRepository.findAll(PageRequest.of(page, size)).map(CustomerResponse::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity <CustomerResponse> getById (@PathVariable String id) {
        return customerRepository.findById(id).map(CustomerResponse::new).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity <CustomerResponse> create (@Valid @RequestBody CreateCustomerRequest request) {
        String id = UUID.randomUUID().toString();
        Customer customer = switch (request.getType().toUpperCase()) {
            case "PREMIUM" -> new PremiumCustomer(id, request.getName());
            case "REGULAR" -> new RegularCustomer(id, request.getName());
            default -> throw new IllegalArgumentException("Invalid type: " + request.getType());
        };

        return ResponseEntity.status(201).body(new CustomerResponse(customerRepository.save(customer)));
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
