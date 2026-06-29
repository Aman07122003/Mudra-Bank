package com.example.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point of the Spring Boot backend application.
 *
 * This class bootstraps the entire application context and
 * starts the embedded web server. It triggers component scanning,
 * autoconfiguration, and enables Spring Boot features such as
 * dependency injection and REST API support.
 */
@SpringBootApplication
public class BackendApplication {

	/**
	 * Main method that starts the Spring Boot application.
	 *
	 * @param args command-line arguments passed during application startup
	 */
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
}