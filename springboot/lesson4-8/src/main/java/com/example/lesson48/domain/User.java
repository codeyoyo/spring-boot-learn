package com.example.lesson48.domain;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private Integer age;

    public User(){

    }

    public User(Long id,String name,Integer age){
        this.id=id;
        this.name=name;
        this.age=age;
    }

    public User(String name,Integer age){
        this.name=name;
        this.age=age;
    }
}
