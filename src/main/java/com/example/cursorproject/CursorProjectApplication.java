package com.example.cursorproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.cursorproject")
public class CursorProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(CursorProjectApplication.class, args);
    }
} 