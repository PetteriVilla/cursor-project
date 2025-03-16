package com.example.cursorproject.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")  // Base path for all endpoints
public class HelloController {
    
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    public HelloController() {
        logger.info("HelloController initialized");
    }
    
    @GetMapping  // Maps to /api
    public ResponseEntity<String> welcome() {
        logger.info("Welcome endpoint called");
        return ResponseEntity.ok("Welcome to Springboot app");
    }

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        logger.info("Hello endpoint called");
        return ResponseEntity.ok("Hello, Cursor Project!");
    }

    // Add a new endpoint to handle potential errors
    @GetMapping("/status")
    public ResponseEntity<String> checkStatus() {
        logger.info("Status endpoint called");
        return ResponseEntity.ok("Application is running successfully");
    }

    // Add an endpoint to demonstrate error handling
    @GetMapping("/divide")
    public ResponseEntity<Double> divide(@RequestParam double a, @RequestParam double b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return ResponseEntity.ok(a / b);
    }
} 