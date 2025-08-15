package com.josemartinezrdev.paymentlinks.infrastructure.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.josemartinezrdev.paymentlinks.application.services.IMerchant;
import com.josemartinezrdev.paymentlinks.config.security.JwtUtil;
import com.josemartinezrdev.paymentlinks.domain.dtos.auth.LoginRequest;
import com.josemartinezrdev.paymentlinks.domain.dtos.auth.RegisterRequest;
import com.josemartinezrdev.paymentlinks.domain.entities.Merchant;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final IMerchant merchantService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, IMerchant merchantService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.merchantService = merchantService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        UserDetails user = (UserDetails) auth.getPrincipal();

        String token = jwtUtil.generateToken(user.getUsername(), new HashMap<>());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterRequest registerRequest) {
        Merchant merchant = new Merchant();
        merchant.setName(registerRequest.getName());
        merchant.setEmail(registerRequest.getEmail());
        merchant.setPasswordHash(registerRequest.getPassword());

        Merchant created = merchantService.create(merchant);

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(registerRequest.getEmail(), registerRequest.getPassword()));

        UserDetails user = (UserDetails) auth.getPrincipal();

        String token = jwtUtil.generateToken(user.getUsername(), new HashMap<>());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("email", created.getEmail());
        response.put("name", created.getName());

        return ResponseEntity.ok(response);
    }

}
