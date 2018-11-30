package com.example.lesson51;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Lesson51Application {

    public static void main(String[] args) {
        SpringApplication.run(Lesson51Application.class, args);
    }
}
