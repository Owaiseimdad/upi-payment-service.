package com.upi.transaction_service.controller;

import com.upi.transaction_service.dto.PaymentRequest;
import com.upi.transaction_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public String initiatePayment(@RequestBody PaymentRequest request) {
        return paymentService.processPayment(request);
    }
}
