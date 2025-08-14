package com.josemartinezrdev.paymentlinks.application.services;

import java.util.List;
import java.util.Optional;

import com.josemartinezrdev.paymentlinks.domain.entities.PaymentLink;

public interface IPaymentLink {

    PaymentLink create(PaymentLink paymentLink);

    Optional<PaymentLink> findById(Long id);

    Optional<PaymentLink> findByReference(String reference);

    List<PaymentLink> findByMerchantId(Long merchantId);
    
    PaymentLink update(PaymentLink paymentLink);

    PaymentLink delete(Long id);

    // @info: Métodos de lógica de negocio

    PaymentLink cancelledLink(Long id);

    PaymentLink markAsPaid(Long id);

    void expireLinks();
}
