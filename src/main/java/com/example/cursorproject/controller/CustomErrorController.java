package com.example.cursorproject.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CustomErrorController implements ErrorController {
    
    private static final Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

    @RequestMapping("/error")
    public ResponseEntity<Map<String, Object>> handleError(HttpServletRequest request) {
        // Get the status code
        Object status = request.getAttribute("jakarta.servlet.error.status_code");
        
        // Get the error message
        Object message = request.getAttribute("jakarta.servlet.error.message");
        
        // Get the exception
        Object exception = request.getAttribute("jakarta.servlet.error.exception");

        // Create a response map
        Map<String, Object> errorDetails = new HashMap<>();
        
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            errorDetails.put("status", statusCode);
            errorDetails.put("error", HttpStatus.valueOf(statusCode).getReasonPhrase());
        }
        
        if (message != null) {
            errorDetails.put("message", message);
        }
        
        if (exception != null) {
            errorDetails.put("exception", exception.toString());
        }

        // Log the error
        logger.error("Error details: {}", errorDetails);

        // Determine the appropriate HTTP status
        HttpStatus httpStatus = status != null 
            ? HttpStatus.valueOf(Integer.parseInt(status.toString())) 
            : HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(errorDetails, httpStatus);
    }
} 