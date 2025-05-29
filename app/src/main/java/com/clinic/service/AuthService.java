package com.clinic.service;

import com.clinic.config.JwtUtil;
import com.clinic.dto.*;
import com.clinic.model.RefreshToken;
import com.clinic.model.Role;
import com.clinic.model.User;
import com.clinic.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, RefreshTokenService refreshTokenService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.refreshTokenService = refreshTokenService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public JwtResponseDTO register(RegisterRequest request, String role) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email is already in use");
        }

        User user = new User();
        user.setUsername(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.valueOf(role.toUpperCase()));

        userRepository.save(user);

        String accessToken = jwtUtil.generateAccessToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getEmail());
        return JwtResponseDTO.builder()
                .accessToken(accessToken)
                .token(refreshToken.getToken()).build();
    }

    public JwtResponseDTO authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        String accessToken = jwtUtil.generateAccessToken( user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getEmail());
        return JwtResponseDTO.builder()
                .accessToken(accessToken)
                .token(refreshToken.getToken()).build();
    }

    public UserResponse getUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }
}


