package com.example.lesson52;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Lesson52Application {

    public static void main(String[] args) {
        SpringApplication.run(Lesson52Application.class, args);
    }
}
