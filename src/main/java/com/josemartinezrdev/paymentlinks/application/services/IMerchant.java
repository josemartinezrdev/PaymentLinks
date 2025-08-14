package com.josemartinezrdev.paymentlinks.application.services;

import java.util.List;
import java.util.Optional;

import com.josemartinezrdev.paymentlinks.domain.entities.Merchant;

public interface IMerchant {
    Merchant create(Merchant merchant);

    Optional<Merchant> findById(Long id);

    Optional<Merchant> findByEmail(String email);

    List<Merchant> findAll();

    Merchant update(Merchant merchant);

    Merchant delete(Long id);    
}
