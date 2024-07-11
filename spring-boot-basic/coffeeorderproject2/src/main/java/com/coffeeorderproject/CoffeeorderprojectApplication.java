package com.coffeeorderproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = { "com.coffeeorderproject.entity" })
@SpringBootApplication
public class CoffeeorderprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoffeeorderprojectApplication.class, args);
	}

}
