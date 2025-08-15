package com.josemartinezrdev.paymentlinks.infrastructure.repositories.payment_link;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.josemartinezrdev.paymentlinks.application.services.IMerchant;
import com.josemartinezrdev.paymentlinks.application.services.IPaymentLink;
import com.josemartinezrdev.paymentlinks.config.security.SecurityUtils;
import com.josemartinezrdev.paymentlinks.domain.entities.Merchant;
import com.josemartinezrdev.paymentlinks.domain.entities.PaymentLink;
import com.josemartinezrdev.paymentlinks.utils.exceptions.GlobalExceptions;

@Service
@Transactional
public class PaymentLinkImp implements IPaymentLink {

    private final PaymentLinkRepository paymentLinkRepository;
    private final IMerchant merchantService;

    public PaymentLinkImp(PaymentLinkRepository paymentLinkRepository, IMerchant merchantService) {
        this.paymentLinkRepository = paymentLinkRepository;
        this.merchantService = merchantService;
    }

    @Override
    public PaymentLink create(PaymentLink paymentLink) {
        paymentLinkRepository.findByReference(paymentLink.getReference()).ifPresent(
                paymentLinkFound -> {
                    throw new GlobalExceptions("La referncia ya existe");
                });

        if (paymentLink.getAmountCent() == null || paymentLink.getAmountCent() <= 0) {
            throw new GlobalExceptions("El monto debe ser mayor a 0");
        }

        return paymentLinkRepository.save(paymentLink);
    }

    @Override
    public Optional<PaymentLink> findById(Long id) {
        Optional<PaymentLink> optionalLink = paymentLinkRepository.findById(id);

        if (optionalLink.isEmpty()) {
            return Optional.empty();
        }

        PaymentLink link = optionalLink.get();

        Merchant currentMerchant = SecurityUtils.getCurrentMerchantFromJWT(merchantService);

        if (!link.getMerchant().getId().equals(currentMerchant.getId())) {
            return Optional.empty();
        }

        return Optional.of(link);
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

        Merchant currentMerchant = SecurityUtils.getCurrentMerchantFromJWT(merchantService);

        if (!existing.getMerchant().getId().equals(currentMerchant.getId())) {
            throw new GlobalExceptions("No tienes permisos para interactuar con este link");
        }

        if (existing.getStatus() == PaymentLink.PaymentStatus.PAID) {
            throw new GlobalExceptions("No se puede cancelar un PaymentLink pago");
        }

        existing.setStatus(PaymentLink.PaymentStatus.CANCELLED);
        return paymentLinkRepository.save(existing);
    }

    @Override
    public List<PaymentLink> findAllCreated() {
        return paymentLinkRepository.findByStatus(PaymentLink.PaymentStatus.CREATED);
    }

    @Override
    public List<PaymentLink> findAllLinks() {
        Merchant currentMerchant = SecurityUtils.getCurrentMerchantFromJWT(merchantService);
        return paymentLinkRepository.findAllByMerchantId(currentMerchant.getId());
    }
}
