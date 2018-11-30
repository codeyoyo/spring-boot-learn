package com.example.lesson35;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@SpringBootApplication
public class Lesson35Application {

    public static void main(String[] args) {
        SpringApplication.run(Lesson35Application.class, args);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    static class UserDto{
        private String userName;
        private LocalDate birthday;
    }

    @RestController
    class HelloController{

        @PostMapping("/user")
        public UserDto user(@RequestBody UserDto userDto){
            return userDto;
        }
    }

    @Bean
    public ObjectMapper serializingObjectMapper(){
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
