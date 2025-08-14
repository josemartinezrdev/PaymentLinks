package com.josemartinezrdev.paymentlinks.application.services;

import java.util.List;

import com.josemartinezrdev.paymentlinks.domain.entities.PaymentAttempt;


public interface IPaymentAttempt {

    PaymentAttempt create(PaymentAttempt paymentAttempt);

    List<PaymentAttempt> findByPaymentLinkId(Long paymentLinkId);

}
