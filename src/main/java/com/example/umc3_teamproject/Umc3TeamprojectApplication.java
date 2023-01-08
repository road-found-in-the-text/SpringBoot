package com.example.umc3_teamproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Umc3TeamprojectApplication {

	public static void main(String[] args) {

		SpringApplication.run(Umc3TeamprojectApplication.class, args);
	}

}
