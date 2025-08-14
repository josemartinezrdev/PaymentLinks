package com.josemartinezrdev.paymentlinks.infrastructure.repositories.payment_link;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.josemartinezrdev.paymentlinks.application.services.IPaymentLink;
import com.josemartinezrdev.paymentlinks.domain.entities.PaymentLink;
import com.josemartinezrdev.paymentlinks.utils.exceptions.GlobalExceptions;

@Service
@Transactional
public class PaymentLinkImp implements IPaymentLink {

    private final PaymentLinkRepository paymentLinkRepository;

    public PaymentLinkImp(PaymentLinkRepository paymentLinkRepository) {
        this.paymentLinkRepository = paymentLinkRepository;
    }

    @Override
    public PaymentLink create(PaymentLink paymentLink) {
        paymentLinkRepository.findByReference(paymentLink.getReference()).ifPresent(
                paymentLinkFound -> {
                    throw new GlobalExceptions("La referncia ya existe");
                });

        if (paymentLink.getAmountCent() <= 0) {
            throw new GlobalExceptions("El monto debe ser mayor a 0");
        }

        return paymentLinkRepository.save(paymentLink);
    }

    @Override
    public Optional<PaymentLink> findById(Long id) {
        return paymentLinkRepository.findById(id);
    }

    @Override
    public Optional<PaymentLink> findByReference(String reference) {
        return paymentLinkRepository.findByReference(reference);
    }

    @Override
    public List<PaymentLink> findByMerchantId(Long merchantId) {
        return paymentLinkRepository.findByMerchantId(merchantId);
    }

    @Override
    public PaymentLink update(PaymentLink paymentLink) {
        PaymentLink existing = paymentLinkRepository.findById(paymentLink.getId())
                .orElseThrow(() -> new GlobalExceptions("El PaymentLink no existe"));

        existing.setAmountCent(paymentLink.getAmountCent());
        existing.setCurrency(paymentLink.getCurrency());
        existing.setDescription(paymentLink.getDescription());

        return paymentLinkRepository.save(existing);
    }

    @Override
    public PaymentLink delete(Long id) {
        PaymentLink existing = paymentLinkRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptions("El PaymentLink no existe"));
        paymentLinkRepository.delete(existing);
        return existing;
    }

    @Override
    public PaymentLink cancelledLink(Long id) {
        PaymentLink existing = paymentLinkRepository.findById(id).orElseThrow(() -> {
            throw new GlobalExceptions("El PaymentLink no existe");
        });

        if (existing.getStatus() == PaymentLink.PaymentStatus.PAID) {
            throw new GlobalExceptions("No se puede cancelar un PaymentLink pago");
        }

        existing.setStatus(PaymentLink.PaymentStatus.CANCELLED);
        return paymentLinkRepository.save(existing);
    }

    @Override
    public PaymentLink markAsPaid(Long id) {
        PaymentLink existing = paymentLinkRepository.findById(id).orElseThrow(() -> {
            throw new GlobalExceptions("El PaymentLink no existe");
        });

        if (existing.getStatus() != PaymentLink.PaymentStatus.CREATED) {
            throw new GlobalExceptions("El PaymentLink no se puede pagar");
        }

        existing.setStatus(PaymentLink.PaymentStatus.PAID);
        existing.setPaidAt(java.time.LocalDateTime.now());

        return paymentLinkRepository.save(existing);
    }
}
