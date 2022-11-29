package com.gate.lck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class LckGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(LckGatewayApplication.class, args);
	}

}
