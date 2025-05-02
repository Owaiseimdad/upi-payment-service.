package com.upi.authentication_service.service;

import com.upi.authentication_service.dto.AuthRequest;
import com.upi.authentication_service.repository.CredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final CredentialRepository credentialRepository;

    public boolean verifyPin(AuthRequest request) {
        return credentialRepository.findById(request.getUserId())
            .map(credential -> credential.getPin().equals(request.getPin()))
            .orElse(false);
    }
}
