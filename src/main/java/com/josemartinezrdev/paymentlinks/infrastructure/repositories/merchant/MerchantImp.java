package com.josemartinezrdev.paymentlinks.infrastructure.repositories.merchant;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.josemartinezrdev.paymentlinks.application.services.IMerchant;
import com.josemartinezrdev.paymentlinks.domain.entities.Merchant;
import com.josemartinezrdev.paymentlinks.utils.exceptions.GlobalExceptions;

@Service
@Transactional
public class MerchantImp implements IMerchant {

    private final MerchantRepository merchantRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public MerchantImp(MerchantRepository merchantRepository, BCryptPasswordEncoder passwordEncoder) {
        this.merchantRepository = merchantRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Merchant create(Merchant merchant) {
        if (merchant.getName() == null || merchant.getName().isBlank()) {
            throw new GlobalExceptions("El Nombre del Merchant no puede estar vacío");
        }
        if (merchant.getEmail() == null || merchant.getEmail().isBlank()) {
            throw new GlobalExceptions("El Email del Merchant no puede estar vacío");
        }
        Optional<Merchant> existing = merchantRepository.findByEmail(merchant.getEmail());
        if (existing.isPresent()) {
            throw new GlobalExceptions("El Email del Merchant ya existe");
        }

        merchant.setPasswordHash(passwordEncoder.encode(merchant.getPasswordHash()));

        return merchantRepository.save(merchant);
    }

    @Override
    public Optional<Merchant> findById(Long id) {
        return merchantRepository.findById(id);
    }

    @Override
    public Optional<Merchant> findByEmail(String email) {
        return merchantRepository.findByEmail(email);
    }

    @Override
    public List<Merchant> findAll() {
        return merchantRepository.findAll();
    }

    @Override
    public Merchant update(Merchant merchant) {
        Merchant existing = merchantRepository.findById(merchant.getId())
                .orElseThrow(() -> new GlobalExceptions("El Merchant no existe"));
        existing.setName(merchant.getName());
        existing.setEmail(merchant.getEmail());
        if (merchant.getPasswordHash() != null && !merchant.getPasswordHash().isBlank()) {
            existing.setPasswordHash(passwordEncoder.encode(merchant.getPasswordHash()));
        }

        return merchantRepository.save(existing);
    }

    @Override
    public Merchant delete(Long id) {
        Merchant existing = merchantRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptions("El Merchant no existe"));
        merchantRepository.delete(existing);
        return existing;
    }

}
