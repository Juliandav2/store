package com.tienda.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table (name = "token_blacklist")
public class BlacklistedToken {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @Column (nullable = false, unique = true, length = 512)
    private String token;

    @Column (name = "blacklisted_at", nullable = false)
    private LocalDateTime blacklistedAt;

    protected BlacklistedToken () {}

    public BlacklistedToken (String token) {
        this.token = token;
        this.blacklistedAt = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getBlacklistedAt() {
        return blacklistedAt;
    }
}
