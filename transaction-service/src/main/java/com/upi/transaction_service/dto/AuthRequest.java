package com.upi.transaction_service.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String userId;
    private String pin;

    public AuthRequest(String userId, String pin) {
        this.userId = userId;
        this.pin = pin;
    }
}
