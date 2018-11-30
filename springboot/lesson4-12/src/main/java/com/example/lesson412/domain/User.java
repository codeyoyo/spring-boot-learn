package com.example.lesson412.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false,length = 5)
    private String name;

    @Column(nullable = false)
    private Integer age;

    public User(){}

    public User(String name,Integer age){
        this.name=name;
        this.age=age;
    }
}
