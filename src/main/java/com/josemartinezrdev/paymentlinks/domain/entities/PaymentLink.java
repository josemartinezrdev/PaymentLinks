package com.josemartinezrdev.paymentlinks.domain.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "payment_links", indexes = {
        @Index(name = "idx_merchant_status", columnList = "merchant_id, status")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uk_reference", columnNames = "reference")
})
public class PaymentLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "merchant_id", nullable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Merchant merchant;

    @Column(nullable = false, unique = true)
    private String reference;

    @Positive
    @Column(name = "amount_cent", nullable = false)
    private Integer amountCent;

    @Column(length = 3, nullable = false)
    private String currency;

    @Column(length = 255)
    @Size(max = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "paid_at", nullable = true)
    private LocalDateTime paidAt;

    @Column(columnDefinition = "json")
    private String metadata;

    public enum PaymentStatus {
        CREATED,
        PAID,
        CANCELLED,
        EXPIRED
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (status == null) {
            status = PaymentStatus.CREATED;
        }
    }

    @Override
    public String toString() {
        return "PaymentLink [id=" + id + ", merchant=" + merchant + ", reference=" + reference + ", amountCent="
                + amountCent + ", currency=" + currency + ", description=" + description + ", status=" + status
                + ", createdAt=" + createdAt + ", paidAt=" + paidAt + ", metadata=" + metadata + "]";
    }

    public PaymentLink() {
    }

    public PaymentLink(Long id, Merchant merchant, String reference, Integer amountCent, String currency,
            String description, PaymentStatus status, LocalDateTime createdAt, LocalDateTime paidAt, String metadata) {
        this.id = id;
        this.merchant = merchant;
        this.reference = reference;
        this.amountCent = amountCent;
        this.currency = currency;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.paidAt = paidAt;
        this.metadata = metadata;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Merchant getMerchant() {
        return this.merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public String getReference() {
        return this.reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Integer getAmountCent() {
        return this.amountCent;
    }

    public void setAmountCent(Integer amountCent) {
        this.amountCent = amountCent;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PaymentStatus getStatus() {
        return this.status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getPaidAt() {
        return this.paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public String getMetadata() {
        return this.metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

}
