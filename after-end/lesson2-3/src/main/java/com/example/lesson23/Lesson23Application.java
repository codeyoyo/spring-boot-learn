package com.example.lesson23;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class Lesson23Application {

    public static void main(String[] args) {
        SpringApplication.run(Lesson23Application.class, args);
    }

    @Slf4j
    static class DataLoader implements CommandLineRunner{

        @Override
        public void run(String... args) throws Exception {
            log.info("Loading data...");
        }
    }

    @Bean
    public DataLoader dataLoader(){
        return new DataLoader();
    }
}
