package com.zkrypto.zkwalletWithCustody;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ZkwalletWithCustodyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZkwalletWithCustodyApplication.class, args);
	}

}
