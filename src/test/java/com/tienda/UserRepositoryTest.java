package com.tienda;

import com.tienda.model.User;
import com.tienda.repository.JpaUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    private JpaUserRepository userRepository;

    @Test
    void shouldSaveAndFindUserByUsername() {
        User user = new User("testuser", "password123", "USER");
        userRepository.save(user);

        Optional<User> found = userRepository.findByUsername("testuser");

        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("testuser");
        assertThat(found.get().getRole()).isEqualTo("USER");
    }

    @Test
    void shouldReturnEmptyWhenUserNotFound() {
        Optional<User> found = userRepository.findByUsername("nonexistent");
        assertThat(found).isEmpty();
    }

    @Test
    void shouldSaveAdminUser() {
        User user = new User("adminuser", "password123", "ADMIN");
        userRepository.save(user);

        Optional<User> found = userRepository.findByUsername("adminuser");

        assertThat(found).isPresent();
        assertThat(found.get().getRole()).isEqualTo("ADMIN");
    }
}
