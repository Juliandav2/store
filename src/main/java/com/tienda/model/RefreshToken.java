package com.tienda.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table (name = "refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @Column (nullable = false, unique = true)
    private String token;

    @Column (nullable = false)
    private String username;

    @Column (name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column (nullable = false)
    private boolean revoked = false;

    protected RefreshToken () {}

    public RefreshToken (String token, String username, LocalDateTime expiresAt) {
        this.token = token;
        this.username = username;
        this.expiresAt = expiresAt;
    }

    public boolean isExpired () {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    public boolean isValid () {
        return !revoked && !isExpired();
    }

    public void revoke () {
        this.revoked = true;
    }

    public long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public boolean isRevoked() {
        return revoked;
    }
}
