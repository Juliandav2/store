package com.tienda.model;

import jakarta.persistence.*;

@Entity
@Table (name = "users")
public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @Column (nullable = false, unique = true)
    private String username;

    @Column (nullable = false)
    private String password;

    @Column (nullable = false)
    private String role;

    protected User () {}

    public User (String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public long getId () {
        return id;
    }

    public String getUsername () {
        return username;
    }

    public String getPassword () {
        return password;
    }

    public String getRole () {
        return role;
    }

}
