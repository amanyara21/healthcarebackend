package com.clinic.controller;

import com.clinic.config.JwtUtil;
import com.clinic.dto.JwtResponseDTO;
import com.clinic.model.RefreshToken;
import com.clinic.service.RefreshTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class RefreshTokenController
{

    private final AuthenticationManager authenticationManager;
    private RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;

    public RefreshTokenController(AuthenticationManager authenticationManager, RefreshTokenService refreshTokenService, JwtUtil jwtUtil){
        this.authenticationManager=authenticationManager;
        this.jwtUtil=jwtUtil;
        this.refreshTokenService=refreshTokenService;
    }


    @PostMapping("auth/refreshToken")
    public JwtResponseDTO refreshToken(@RequestBody String token){
        return refreshTokenService.findByToken(token)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(userInfo -> {
                    String accessToken = jwtUtil.generateAccessToken(userInfo);
                    return JwtResponseDTO.builder()
                            .accessToken(accessToken)
                            .token(token).build();
                }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
    }

}
