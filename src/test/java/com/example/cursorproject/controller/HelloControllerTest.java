package com.example.cursorproject.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelloController.class)
public class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testWelcomeEndpoint() throws Exception {
        mockMvc.perform(get("/api"))
               .andExpect(status().isOk())
               .andExpect(content().string("Welcome to Springboot app"));
    }

    @Test
    public void testHelloEndpoint() throws Exception {
        mockMvc.perform(get("/api/hello"))
               .andExpect(status().isOk())
               .andExpect(content().string("Hello, Cursor Project!"));
    }

    @Test
    public void testStatusEndpoint() throws Exception {
        mockMvc.perform(get("/api/status"))
               .andExpect(status().isOk())
               .andExpect(content().string("Application is running successfully"));
    }
} 