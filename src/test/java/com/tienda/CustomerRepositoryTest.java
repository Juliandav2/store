package com.tienda;

import com.tienda.model.Customer;
import com.tienda.model.PremiumCustomer;
import com.tienda.model.RegularCustomer;
import com.tienda.repository.JpaCustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class CustomerRepositoryTest {

    @Autowired
    private JpaCustomerRepository customerRepository;

    @Test
    void shouldSavePremiumCustomer() {
        Customer customer = new PremiumCustomer("c1", "Julian");
        customerRepository.save(customer);

        Optional<Customer> found = customerRepository.findById("c1");

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Julian");
        assertThat(found.get()).isInstanceOf(PremiumCustomer.class);
    }

    @Test
    void shouldSaveRegularCustomer() {
        Customer customer = new RegularCustomer("c2", "Pedro");
        customerRepository.save(customer);

        Optional<Customer> found = customerRepository.findById("c2");

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Pedro");
        assertThat(found.get()).isInstanceOf(RegularCustomer.class);
    }

    @Test
    void shouldReturnEmptyWhenCustomerNotFound() {
        Optional<Customer> found = customerRepository.findById("nonexistent");
        assertThat(found).isEmpty();
    }

    @Test
    void shouldDeleteCustomer() {
        Customer customer = new PremiumCustomer("c3", "Maria");
        customerRepository.save(customer);

        customerRepository.deleteById("c3");

        assertThat(customerRepository.findById("c3")).isEmpty();
    }
}