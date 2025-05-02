package com.upi.account_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Account {
    @Id
    private String accountId;
    private String userId;
    private BigDecimal balance;
}
