package com.hutech.easylearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients

public class EasylearningApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasylearningApplication.class, args);
	}

}
