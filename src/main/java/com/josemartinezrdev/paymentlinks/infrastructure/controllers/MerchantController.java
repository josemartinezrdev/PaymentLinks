package com.josemartinezrdev.paymentlinks.infrastructure.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.josemartinezrdev.paymentlinks.application.services.IMerchant;
import com.josemartinezrdev.paymentlinks.domain.entities.Merchant;
import com.josemartinezrdev.paymentlinks.utils.exceptions.GlobalExceptions;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/merchants")
public class MerchantController {
    private final IMerchant merchantService;

    public MerchantController(IMerchant merchantService) {
        this.merchantService = merchantService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Merchant> getById(@PathVariable Long id) {
        Merchant merchant = merchantService.findById(id).orElse(null);
        if (merchant == null) {
            throw new GlobalExceptions("El Merchant no existe");
        }
        return ResponseEntity.ok(merchant);
    }

    @GetMapping
    public ResponseEntity<List<Merchant>> getAll() {
        return ResponseEntity.ok(merchantService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Merchant> update(@PathVariable Long id, @RequestBody Merchant merchant) {
        Merchant existing = merchantService.findById(id).orElse(null);
        if (existing == null) {
            throw new GlobalExceptions("El Merchant No existe");
        }

        merchant.setId(id);
        Merchant updated = merchantService.update(merchant);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Merchant> delete(@PathVariable Long id) {
        Merchant deleted = merchantService.delete(id);
        return ResponseEntity.ok(deleted);
    }

}
