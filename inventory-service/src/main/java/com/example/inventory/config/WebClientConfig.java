package com.example.inventory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient productWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8081")
                .defaultHeader("X-API-KEY", "inventory-service-key")
                .build();
    }
}
