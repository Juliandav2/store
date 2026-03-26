package com.tienda.repository;

import com.tienda.model.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTokenBlacklistRepository extends JpaRepository<BlacklistedToken, Long> {
    boolean existsByToken (String token);
}
