package com.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
@EnableEurekaServer
// @EnableDiscoveryClient
public class CmsRegistryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmsRegistryServerApplication.class, args);
	}

}
