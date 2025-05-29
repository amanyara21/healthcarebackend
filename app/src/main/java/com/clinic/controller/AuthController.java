package com.clinic.controller;


import com.clinic.dto.*;
import com.clinic.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/")
    public String register() {
        return "Hello";
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponseDTO> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request, "user"));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody LoginRequest request) {
        System.out.println(request.getEmail());
        return ResponseEntity.ok(authService.authenticate(request));
    }
    @GetMapping("/getuser")
    public ResponseEntity<UserResponse> getUser(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(authService.getUser(userDetails.getUsername()));
    }
}
