package com.josemartinezrdev.paymentlinks.infrastructure.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.josemartinezrdev.paymentlinks.application.services.IPaymentAttempt;
import com.josemartinezrdev.paymentlinks.application.services.IPaymentLink;
import com.josemartinezrdev.paymentlinks.domain.entities.PaymentAttempt;
import com.josemartinezrdev.paymentlinks.domain.entities.PaymentLink;
import com.josemartinezrdev.paymentlinks.utils.exceptions.GlobalExceptions;

@RestController
@RequestMapping("/payment-link")
public class PaymentLinkController {

    private final IPaymentLink paymentLinkService;
    private final IPaymentAttempt paymentAttemptService;

    public PaymentLinkController(IPaymentLink paymentLinkService, IPaymentAttempt paymentAttemptService) {
        this.paymentLinkService = paymentLinkService;
        this.paymentAttemptService = paymentAttemptService;
    }

    @PostMapping
    public ResponseEntity<PaymentLink> create(@RequestBody PaymentLink paymentLink) {
        return ResponseEntity.ok(paymentLinkService.create(paymentLink));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentLink> getById(@PathVariable Long id) {
        PaymentLink link = paymentLinkService.findById(id).orElse(null);
        if (link == null) {
            throw new GlobalExceptions("El link no existe");
        }
        return ResponseEntity.ok(link);
    }

    @GetMapping("/merchant/{merchantId}")
    public ResponseEntity<List<PaymentLink>> getByMerchant(@PathVariable Long merchantId) {
        return ResponseEntity.ok(paymentLinkService.findByMerchantId(merchantId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentLink> update(@PathVariable Long id, @RequestBody PaymentLink paymentLink) {
        PaymentLink existing = paymentLinkService.findById(id).orElse(null);
        if (existing == null) {
            throw new GlobalExceptions("No existe el link");
        }
        paymentLink.setId(id);
        PaymentLink updated = paymentLinkService.update(paymentLink);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PaymentLink> delete(@PathVariable Long id) {
        PaymentLink deleted = paymentLinkService.delete(id);
        return ResponseEntity.ok(deleted);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<PaymentLink> cancel(@PathVariable Long id) {
        PaymentLink cancelled = paymentLinkService.cancelledLink(id);
        return ResponseEntity.ok(cancelled);
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<PaymentAttempt> pay(@PathVariable Long id,
            @RequestHeader(value = "Idempotency-Key", required = false) String idempotencyKey) {

        PaymentAttempt attempt = paymentAttemptService.attemptPayment(id, idempotencyKey);
        return ResponseEntity.ok(attempt);
    }

    @GetMapping
    public ResponseEntity<List<PaymentLink>> getAllByCurrentMerchant() {
        return ResponseEntity.ok(paymentLinkService.findAllLinks());
    }
}
