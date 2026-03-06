package com.tienda.repository;

import com.tienda.model.User;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository <User, Long> {
    Optional <User> findByUsername (String username);
}
