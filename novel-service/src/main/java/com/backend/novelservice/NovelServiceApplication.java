package com.backend.novelservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication(scanBasePackages = "com.backend")
public class NovelServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NovelServiceApplication.class, args);
    }
}