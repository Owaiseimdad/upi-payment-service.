package com.upi.account_service.controller;

import com.upi.account_service.dto.AccountRequest;
import com.upi.account_service.dto.ValidateRequest;
import com.upi.account_service.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/validate")
    public boolean validate(@RequestBody ValidateRequest request) {
        return accountService.validatePayment(request);
    }

    @PostMapping("/debit")
    public boolean debit(@RequestBody AccountRequest request) {
        return accountService.debit(request);
    }

    @PostMapping("/credit")
    public boolean credit(@RequestBody AccountRequest request) {
        return accountService.credit(request);
    }
}
