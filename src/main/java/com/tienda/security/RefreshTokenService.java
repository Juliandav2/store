package com.tienda.security;

import com.tienda.model.RefreshToken;
import com.tienda.repository.JpaRefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final JpaRefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService (JpaRefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken createRefreshToken (String username) {
        RefreshToken refreshToken = new RefreshToken(UUID.randomUUID().toString(), username, LocalDateTime.now().plusDays(7));
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken validateRefreshToken (String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Refresh token not found"));
        if (!refreshToken.isValid()) {
            throw new RuntimeException("Refresh is expired or revoke");
        }

        return refreshToken;
    }

    @Transactional
    public void revokeRefreshToken (String token) {
        refreshTokenRepository.findByToken(token).ifPresent(RefreshToken::revoke);
    }

    @Transactional
    public void revokeAllUserTokens (String username) {
        refreshTokenRepository.deleteByUsername(username);
    }
}
