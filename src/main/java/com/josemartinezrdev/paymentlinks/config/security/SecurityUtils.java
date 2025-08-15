package com.josemartinezrdev.paymentlinks.config.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import com.josemartinezrdev.paymentlinks.domain.entities.Merchant;
import com.josemartinezrdev.paymentlinks.utils.exceptions.GlobalExceptions;
import com.josemartinezrdev.paymentlinks.application.services.IMerchant;

public class SecurityUtils {

    public static Merchant getCurrentMerchantFromJWT(IMerchant merchantService) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return merchantService.findByEmail(email).orElseThrow(() -> new GlobalExceptions("Merchant no encontrado"));
    }

}
