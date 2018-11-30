package com.example.lesson42.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false, columnDefinition = "")
    private String name;

    @Column(nullable = false, columnDefinition = "0")
    private Integer age;

    public User() {
    }

    public User(String n, Integer a) {
        this.name = n;
        this.age = a;
    }
}
