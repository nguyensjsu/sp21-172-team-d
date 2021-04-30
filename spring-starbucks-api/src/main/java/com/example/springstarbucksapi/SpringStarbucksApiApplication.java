package com.example.springstarbucksapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringStarbucksApiApplication {

	private static final Logger log = LoggerFactory.getLogger(SpringStarbucksApiApplication.class);

	public static void main(String[] args) {
		log.info("Starting application");
		SpringApplication.run(SpringStarbucksApiApplication.class, args);
	}

}
