package com.harsh.photo.accounts.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PhotoAppAccountsMgmtApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoAppAccountsMgmtApiApplication.class, args);
	}

}
