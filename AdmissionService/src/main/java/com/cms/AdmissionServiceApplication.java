package com.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "com.cms.model", "com.cms.controller", "com.cms.service", "com.cms.exception",
		"com.cms.validation", "com.*", "com.cms.proxy", "com.cms.*" })
@EnableMongoRepositories("com.cms.repository")
@EnableAspectJAutoProxy
@EnableDiscoveryClient
public class AdmissionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdmissionServiceApplication.class, args);
	}

}
