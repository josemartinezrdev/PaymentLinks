package com.josemartinezrdev.paymentlinks.infrastructure.repositories.merchant;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.josemartinezrdev.paymentlinks.domain.entities.Merchant;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    Optional<Merchant> findByEmail(String email);
}
