package com.tienda.controller;

import com.tienda.dto.AuthResponse;
import com.tienda.dto.RefreshTokenRequest;
import com.tienda.dto.RegisterRequest;
import com.tienda.model.RefreshToken;
import com.tienda.model.User;
import com.tienda.repository.JpaUserRepository;
import com.tienda.security.JwtService;
import com.tienda.security.RefreshTokenService;
import com.tienda.service.EmailService;
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
    private final RefreshTokenService refreshTokenService;
    private final EmailService emailService;

    public AuthController (JpaUserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, RefreshTokenService refreshTokenService, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
        this.emailService = emailService;

    }

    @PostMapping ("/register")
    public ResponseEntity <AuthResponse> register (@RequestBody RegisterRequest request) {
        log.info("POST /auth/register - username={}", request.getUsername());
        String role = request.getRole() != null ? request.getRole().toUpperCase() : "USER";
        User user = new User(request.getUsername(), passwordEncoder.encode(request.getPassword()), role);
        userRepository.save(user);
        String accessToken = jwtService.generateToken(user.getUsername());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUsername());
        emailService.sendWelcomeEmail(request.getUsername(), request.getUsername());
        log.info("User registered: {}", user.getUsername());
        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken.getToken()));
    }


    @PostMapping ("/login")
    public ResponseEntity <AuthResponse> login (@RequestBody RegisterRequest request) {
        log.info("POST /auth/login - username={}", request.getUsername());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        String accessToken = jwtService.generateToken(request.getUsername());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(request.getUsername());
        log.info("User logged in: {}", request.getUsername());
        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken.getToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh (@RequestBody RefreshTokenRequest request) {
        log.info("POST /auth/refresh");
        RefreshToken refreshToken = refreshTokenService.validateRefreshToken(request.getRefreshToken());
        String accessToken = jwtService.generateToken(refreshToken.getUsername());
        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(refreshToken.getUsername());
        refreshTokenService.revokeRefreshToken(request.getRefreshToken());
        return ResponseEntity.ok(new AuthResponse(accessToken, newRefreshToken.getToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout (@RequestBody RefreshTokenRequest request) {
        log.info("POST /auth/logout");
        refreshTokenService.revokeAllUserTokens(refreshTokenService.validateRefreshToken(request.getRefreshToken()).getUsername());
        return ResponseEntity.noContent().build();
    }

}
