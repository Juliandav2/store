package com.tienda.usecases;

import com.tienda.model.RefreshToken;
import com.tienda.repository.JpaRefreshTokenRepository;
import com.tienda.security.RefreshTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RefreshTokenServiceTest {

    private JpaRefreshTokenRepository refreshTokenRepository;
    private RefreshTokenService refreshTokenService;

    @BeforeEach
    void setUp() {
        refreshTokenRepository = Mockito.mock(JpaRefreshTokenRepository.class);
        refreshTokenService = new RefreshTokenService(refreshTokenRepository);
    }

    @Test
    void shouldCreateRefreshToken() {
        when(refreshTokenRepository.save(any(RefreshToken.class)))
                .thenAnswer(i -> i.getArgument(0));

        RefreshToken token = refreshTokenService.createRefreshToken("julian");

        assertThat(token).isNotNull();
        assertThat(token.getUsername()).isEqualTo("julian");
        assertThat(token.isRevoked()).isFalse();
        assertThat(token.getExpiresAt()).isAfter(LocalDateTime.now());
        verify(refreshTokenRepository, times(1)).save(any(RefreshToken.class));
    }

    @Test
    void shouldValidateValidToken() {
        RefreshToken token = new RefreshToken("abc123", "julian", LocalDateTime.now().plusDays(7));
        when(refreshTokenRepository.findByToken("abc123")).thenReturn(Optional.of(token));

        RefreshToken result = refreshTokenService.validateRefreshToken("abc123");

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("julian");
    }

    @Test
    void shouldThrowWhenTokenNotFound() {
        when(refreshTokenRepository.findByToken("invalid")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> refreshTokenService.validateRefreshToken("invalid"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("not found");
    }

    @Test
    void shouldThrowWhenTokenExpired() {
        RefreshToken token = new RefreshToken("abc123", "julian", LocalDateTime.now().minusDays(1));
        when(refreshTokenRepository.findByToken("abc123")).thenReturn(Optional.of(token));

        assertThatThrownBy(() -> refreshTokenService.validateRefreshToken("abc123"))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void shouldRevokeToken() {
        RefreshToken token = new RefreshToken("abc123", "julian", LocalDateTime.now().plusDays(7));
        when(refreshTokenRepository.findByToken("abc123")).thenReturn(Optional.of(token));
        when(refreshTokenRepository.save(any(RefreshToken.class))).thenAnswer(i -> i.getArgument(0));

        refreshTokenService.revokeRefreshToken("abc123");

        assertThat(token.isRevoked()).isTrue();
    }
}