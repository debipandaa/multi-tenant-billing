package com.debii.metering_billing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MeteringBillingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeteringBillingApplication.class, args);
	}

}
