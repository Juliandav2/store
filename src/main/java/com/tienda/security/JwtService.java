package com.tienda.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET = "miClaveSecretaSuperSeguraParaJWT1234567890ABCDEFGHIJ";
    private static final long EXPIRATION = 1000 * 60 * 60 * 24; // 24 hours

    private SecretKey getKey () {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken (String username) {
        return Jwts.builder().subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getKey()).compact();
    }

    public String extractUsername (String token) {
        return  Jwts.parser().verifyWith(getKey())
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
    }

    public boolean isTokenValid (String token) {
        try {
            extractUsername(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
