package com.tienda;

import com.tienda.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @Test
    void shouldGenerateToken() {
        String token = jwtService.generateToken("julian");
        assertThat(token).isNotNull();
        assertThat(token).isNotBlank();
    }

    @Test
    void shouldExtractUsername() {
        String token = jwtService.generateToken("julian");
        String username = jwtService.extractUsername(token);
        assertThat(username).isEqualTo("julian");
    }

    @Test
    void shouldValidateToken() {
        String token = jwtService.generateToken("julian");
        assertThat(jwtService.isTokenValid(token)).isTrue();
    }

    @Test
    void shouldRejectInvalidToken() {
        assertThat(jwtService.isTokenValid("token.invalido.aqui")).isFalse();
    }
}
