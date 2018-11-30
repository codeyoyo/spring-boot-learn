package com.example.lesson46.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class User {

    @Id
    private Long id;

    private String username;
    private Integer age;

    public User() {
    }

    public User(Long id, String username, Integer age) {
        this.id = id;
        this.username = username;
        this.age = age;
    }
}
