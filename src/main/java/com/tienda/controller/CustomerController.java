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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.UUID;

@Tag(name = "Customers", description = "Customer management endpoints")
@RestController
@RequestMapping ("/customers")
public class CustomerController {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CustomerController.class);
    private final JpaCustomerRepository customerRepository;

    public CustomerController (JpaCustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Operation(summary = "Get all customers")
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity <org.springframework.data.domain.Page<CustomerResponse>> getAll (@RequestParam (defaultValue = "0") int page, @RequestParam (defaultValue = "10") int size) {
        log.info("GET /customers - page={}, size={}", page, size);
        return ResponseEntity.ok(customerRepository.findAll(PageRequest.of(page, size)).map(CustomerResponse::new));
    }

    @Operation (summary = "Get customer by ID")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity <CustomerResponse> getById (@PathVariable String id) {
        log.info("GET /customers/{}", id);
        return customerRepository.findById(id).map(CustomerResponse::new).map(ResponseEntity::ok).orElseGet(() -> {log.warn("Customer not found: {}", id);
            return ResponseEntity.notFound().build();
        });
    }

    @Operation(summary = "Create customer", description = "Requires ADMIN role. Type must be PREMIUM or REGULAR")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity <CustomerResponse> create (@Valid @RequestBody CreateCustomerRequest request) {
        log.info("POST /customers - name={}, type={}", request.getName(), request.getType());
        String id = UUID.randomUUID().toString();
        Customer customer = switch (request.getType().toUpperCase()) {
            case "PREMIUM" -> new PremiumCustomer(id, request.getName());
            case "REGULAR" -> new RegularCustomer(id, request.getName());
            default -> throw new IllegalArgumentException("Invalid type: " + request.getType());
        };

        CustomerResponse response = new CustomerResponse(customerRepository.save(customer));
        log.info("Customer created: {}", response.getId());
        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Delete customer", description = "Requires ADMIN role")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping ("/{id}")
    public ResponseEntity <Void> delete (@PathVariable String id) {
        log.info("DELETE /customers/{}", id);
        if (!customerRepository.existsById(id)) {
            log.warn("Customer not found for delete: {}", id);
            return ResponseEntity.notFound().build();
        }

        customerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
