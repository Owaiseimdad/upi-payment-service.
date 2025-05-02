package com.upi.authentication_service.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String userId;
    private String pin;
}
