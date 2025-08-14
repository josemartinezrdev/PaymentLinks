package com.josemartinezrdev.paymentlinks.infrastructure.repositories.payment_attempt;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.josemartinezrdev.paymentlinks.domain.entities.PaymentAttempt;

@Repository
public interface PaymentAttemptRepository extends JpaRepository<PaymentAttempt, Long> {

    List<PaymentAttempt> findByPaymentLinkId(Long paymentLinkId);

    boolean existsByPaymentLinkIdAndIdempotencyKey(Long paymentLinkId, String idempotencyKey);

}
