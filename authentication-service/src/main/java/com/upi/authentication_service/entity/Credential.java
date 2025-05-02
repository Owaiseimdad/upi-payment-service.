package com.upi.authentication_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Credential {
    @Id
    private String userId;
    private String pin;
}
