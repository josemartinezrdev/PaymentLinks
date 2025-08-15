package com.josemartinezrdev.paymentlinks.infrastructure.repositories.payment_attempt;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.josemartinezrdev.paymentlinks.application.services.IMerchant;
import com.josemartinezrdev.paymentlinks.application.services.IPaymentAttempt;
import com.josemartinezrdev.paymentlinks.application.services.IPaymentLink;
import com.josemartinezrdev.paymentlinks.config.security.SecurityUtils;
import com.josemartinezrdev.paymentlinks.domain.entities.Merchant;
import com.josemartinezrdev.paymentlinks.domain.entities.PaymentAttempt;
import com.josemartinezrdev.paymentlinks.domain.entities.PaymentLink;
import com.josemartinezrdev.paymentlinks.utils.exceptions.GlobalExceptions;

@Service
@Transactional
public class PaymentAttemptImp implements IPaymentAttempt {

    private final PaymentAttemptRepository paymentAttemptRepository;
    private final IPaymentLink paymentLinkService;
    private final IMerchant merchantService;

    public PaymentAttemptImp(PaymentAttemptRepository paymentAttemptRepository, IPaymentLink paymentLinkService,
            IMerchant merchantService) {
        this.paymentAttemptRepository = paymentAttemptRepository;
        this.paymentLinkService = paymentLinkService;
        this.merchantService = merchantService;
    }

    @Override
    public List<PaymentAttempt> findByPaymentLinkId(Long paymentLinkId) {
        return paymentAttemptRepository.findByPaymentLinkId(paymentLinkId);
    }

    @Override
    public PaymentAttempt attemptPayment(Long paymentLinkId, String idempotencyKey) {
        PaymentLink link = paymentLinkService.findById(paymentLinkId)
                .orElseThrow(() -> new GlobalExceptions("Link no encontrado"));

        Merchant currentMerchant = SecurityUtils.getCurrentMerchantFromJWT(merchantService);

        if (!link.getMerchant().getId().equals(currentMerchant.getId())) {
            throw new GlobalExceptions("No tienes permisos para interactuar con este link");
        }

        if (link.getStatus() != PaymentLink.PaymentStatus.CREATED) {
            throw new GlobalExceptions("El link no puede ser pagado, su estado es: " + link.getStatus());
        }

        if (idempotencyKey != null) {
            paymentAttemptRepository.findByPaymentLinkIdAndIdempotencyKey(paymentLinkId, idempotencyKey)
                    .ifPresent(existing -> {
                        throw new GlobalExceptions("Intento de pago duplicado");
                    });
        }

        PaymentAttempt attempt = new PaymentAttempt();
        attempt.setPaymentLink(link);

        if (idempotencyKey == null) {
            attempt.setIdempotencyKey(UUID.randomUUID().toString());
        } else {
            attempt.setIdempotencyKey(idempotencyKey);
        }

        try {
            link.setStatus(PaymentLink.PaymentStatus.PAID);
            paymentLinkService.update(link);

            attempt.setStatus(PaymentAttempt.Status.SUCCESS);
        } catch (Exception e) {
            attempt.setStatus(PaymentAttempt.Status.FAILED);
            attempt.setReason(e.getMessage());
        }

        return paymentAttemptRepository.save(attempt);
    }

}
