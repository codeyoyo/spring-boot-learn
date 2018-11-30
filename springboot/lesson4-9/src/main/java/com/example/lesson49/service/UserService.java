package com.example.lesson49.service;

public interface UserService {

    void create(String name,Integer age);

    void deleteByName(String name);

    Integer getAllUsers();

    void deleteAllUsers();
}
