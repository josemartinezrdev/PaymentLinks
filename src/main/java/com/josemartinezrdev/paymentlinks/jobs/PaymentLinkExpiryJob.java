package com.josemartinezrdev.paymentlinks.jobs;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.josemartinezrdev.paymentlinks.application.services.IPaymentLink;
import com.josemartinezrdev.paymentlinks.domain.entities.PaymentLink;

@Component
public class PaymentLinkExpiryJob {

    private static final Logger logger = LoggerFactory.getLogger(PaymentLinkExpiryJob.class);

    private final IPaymentLink paymentLinkService;

    public PaymentLinkExpiryJob(IPaymentLink paymentLinkService) {
        this.paymentLinkService = paymentLinkService;
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void expireLink() {
        logger.info("Ejecutando PaymentLinkExpiryJob a las {}", LocalDateTime.now());

        List<PaymentLink> links = paymentLinkService.findAllCreated();
        LocalDateTime now = LocalDateTime.now();

        for (PaymentLink link : links) {
            if (link.getExpiresAt() != null && link.getExpiresAt().isBefore(now)) {
                link.setStatus(PaymentLink.PaymentStatus.EXPIRED);
                paymentLinkService.update(link);
                logger.info("Link {} expirado automáticamente", link.getReference());
            }
        }
    }
}
