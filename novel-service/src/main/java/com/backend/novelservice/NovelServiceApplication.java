package com.backend.novelservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.backend")
public class NovelServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NovelServiceApplication.class, args);
    }
}