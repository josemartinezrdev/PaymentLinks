package com.josemartinezrdev.paymentlinks.infrastructure.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.josemartinezrdev.paymentlinks.application.services.IPaymentAttempt;
import com.josemartinezrdev.paymentlinks.domain.entities.PaymentAttempt;

@RestController
@RequestMapping("/payment-attempt")
public class PaymentAttemptController {

    private final IPaymentAttempt paymentAttemptService;

    public PaymentAttemptController(IPaymentAttempt paymentAttemptService) {
        this.paymentAttemptService = paymentAttemptService;
    }

    @GetMapping("/link/{paymentLinkId}")
    public ResponseEntity<List<PaymentAttempt>> getByPaymentLink(@PathVariable Long paymentLinkId) {
        return ResponseEntity.ok(paymentAttemptService.findByPaymentLinkId(paymentLinkId));
    }

}
