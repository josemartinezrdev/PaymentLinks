package com.josemartinezrdev.paymentlinks.infrastructure.repositories.payment_link;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.josemartinezrdev.paymentlinks.domain.entities.PaymentLink;

@Repository
public interface PaymentLinkRepository extends JpaRepository<PaymentLink, Long> {

    Optional<PaymentLink> findByReference(String reference);

    List<PaymentLink> findByMerchantId(Long merchantId);

    List<PaymentLink> findByStatus(PaymentLink.PaymentStatus status);

    List<PaymentLink> findAllByMerchantId(Long merchantId);

}
