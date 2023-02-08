package com.example.umc3_teamproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

// (exclude = ContextRegionProviderAutoConfiguration.class)
@SpringBootApplication
@EnableJpaAuditing   // created_at, updated_at 값 반영 가능

@ComponentScan(basePackages = {"com.example"})
@EnableScheduling
public class Umc3TeamprojectApplication {
	static {
		System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
	}
	public static void main(String[] args) {
		SpringApplication.run(Umc3TeamprojectApplication.class, args);
	}
}