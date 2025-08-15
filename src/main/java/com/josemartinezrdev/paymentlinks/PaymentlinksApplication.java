package com.josemartinezrdev.paymentlinks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PaymentlinksApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentlinksApplication.class, args);
	}

}
