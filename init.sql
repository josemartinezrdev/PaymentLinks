INSERT INTO merchants (id, name, email, password_hash, created_at)
VALUES (1, 'User Demo', 'demo@merchant.test', '$2a$10$S.xxOyQMmiIM5AOtHODgReF4MMI9KA089AjnNW89xasMpZ45TBWIa', NOW());

INSERT INTO payment_links (id, merchant_id, reference, amount_cent, currency, description, status, created_at, expires_at)
VALUES 
(1, 1, 'REF-EXPIRED', 1000, 'COP', 'Link ya expirado', 'EXPIRED', NOW() - INTERVAL 2 HOUR, NOW() - INTERVAL 1 HOUR),
(2, 1, 'REF-CREATED', 2000, 'COP', 'Link cercano a expirar', 'CREATED', NOW() - INTERVAL 10 MINUTE, NOW() + INTERVAL 20 MINUTE),
(3, 1, 'REF-PAID', 1500, 'COP', 'Link ya pagado', 'PAID', NOW() - INTERVAL 1 HOUR, NOW() + INTERVAL 1 HOUR);
