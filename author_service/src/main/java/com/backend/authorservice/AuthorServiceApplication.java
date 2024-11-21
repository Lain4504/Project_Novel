package com.backend.authorservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AuthorServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthorServiceApplication.class, args);
    }
}
