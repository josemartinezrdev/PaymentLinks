package com.josemartinezrdev.paymentlinks.application.services;

import java.util.List;

import com.josemartinezrdev.paymentlinks.domain.entities.PaymentAttempt;

public interface IPaymentAttempt {

    List<PaymentAttempt> findByPaymentLinkId(Long paymentLinkId);

    PaymentAttempt attemptPayment(Long paymentLinkId, String idempotencyKey);

}
