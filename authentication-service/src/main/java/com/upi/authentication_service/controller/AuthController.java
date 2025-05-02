package com.upi.authentication_service.controller;

import com.upi.authentication_service.dto.AuthRequest;
import com.upi.authentication_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/verify")
    public boolean verify(@RequestBody AuthRequest request) {
        return authService.verifyPin(request);
    }
}