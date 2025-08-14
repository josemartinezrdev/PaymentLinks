package com.josemartinezrdev.paymentlinks.infrastructure.repositories.payment_attempt;

import java.util.List;

import org.springframework.stereotype.Service;

import com.josemartinezrdev.paymentlinks.application.services.IPaymentAttempt;
import com.josemartinezrdev.paymentlinks.domain.entities.PaymentAttempt;
import com.josemartinezrdev.paymentlinks.utils.exceptions.GlobalExceptions;

@Service
public class PaymentAttemptImp implements IPaymentAttempt {

    private final PaymentAttemptRepository paymentAttemptRepository;

    public PaymentAttemptImp(PaymentAttemptRepository paymentAttemptRepository) {
        this.paymentAttemptRepository = paymentAttemptRepository;
    }

    @Override
    public PaymentAttempt create(PaymentAttempt paymentAttempt) {
        if (paymentAttempt.getIdempotencyKey() != null &&
                paymentAttemptRepository.existsByPaymentLinkIdAndIdempotencyKey(paymentAttempt.getPaymentLink().getId(),
                        paymentAttempt.getIdempotencyKey())) {
            throw new GlobalExceptions("Intento de pago duplicado (idempotency)");
        }

        return paymentAttemptRepository.save(paymentAttempt);
    }

    @Override
    public List<PaymentAttempt> findByPaymentLinkId(Long paymentLinkId) {
        return paymentAttemptRepository.findByPaymentLinkId(paymentLinkId);
    }

}
