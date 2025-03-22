package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

@SpringBootApplication
public class TodoApplication {
    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
    }

    @Configuration
    public class WebConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/**")
                    .addResourceLocations("classpath:/static/")
                    .resourceChain(true)
                    .addResolver(new PathResourceResolver() {
                        @Override
                        protected Resource getResource(String resourcePath, Resource location) throws IOException {
                            Resource requestedResource = location.createRelative(resourcePath);
                            
                            // If the requested resource doesn't exist, serve index.html
                            if (!requestedResource.exists() || !requestedResource.isReadable()) {
                                return new ClassPathResource("/static/index.html");
                            }
                            
                            return requestedResource;
                        }
                    });
        }

        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            // Forward any unmatched paths to index.html for client-side routing
            registry.addViewController("/{path:[^\\.]*}")
                    .setViewName("forward:/index.html");
        }
    }
} 