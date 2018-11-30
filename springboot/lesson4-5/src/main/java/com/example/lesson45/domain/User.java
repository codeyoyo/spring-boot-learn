package com.example.lesson45.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private static final long serialVersionUID=-1L;

    private String username;

    private Integer age;

    public User(){}

    public User(String n,Integer a){
        this.username=n;
        this.age=a;
    }
}
