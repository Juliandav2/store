package com.tienda.controller;

import com.tienda.dto.AuthResponse;
import com.tienda.dto.RegisterRequest;
import com.tienda.model.User;
import com.tienda.repository.JpaUserRepository;
import com.tienda.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/auth")
public class AuthController {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AuthController.class);
    private final JpaUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthController (JpaUserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping ("/register")
    public ResponseEntity <AuthResponse> register (@RequestBody RegisterRequest request) {
        log.info("POST /auth/register - username={}", request.getUsername());
        String role = request.getRole() != null ? request.getRole().toUpperCase() : "USER";
        User user = new User(request.getUsername(), passwordEncoder.encode(request.getPassword()), role);
        userRepository.save(user);
        String token = jwtService.generateToken(user.getUsername());
        log.info("User registered: {}", user.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }


    @PostMapping ("/login")
    public ResponseEntity <AuthResponse> login (@RequestBody RegisterRequest request) {
        log.info("POST /auth/login - username={}", request.getUsername());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        String token = jwtService.generateToken(request.getUsername());
        log.info("User logged in: {}", request.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }

}
