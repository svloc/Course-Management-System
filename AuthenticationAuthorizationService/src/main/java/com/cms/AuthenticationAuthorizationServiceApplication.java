package com.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
@SpringBootApplication
@ComponentScan({"com.*"})
@EnableMongoRepositories({"com.*"})
@EnableFeignClients
public class AuthenticationAuthorizationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationAuthorizationServiceApplication.class, args);
	}

}
