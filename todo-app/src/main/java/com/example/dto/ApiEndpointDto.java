package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiEndpointDto {
    private String path;
    private String method;
    private String description;
    private boolean requiresInput;
} 