package com.example.lesson41.service;

public interface UserService {

    void create(String name,Integer age);

    Integer getAllUsers();

    void deleteByName(String name);

    void delelteAllUsers();
}
