package com.example.bank_card_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com")
@EntityScan("com.entity")
@EnableJpaRepositories("com.repository")
public class BankCardManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankCardManagementApplication.class, args);
	}

}
