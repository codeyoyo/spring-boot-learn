package com.example.lesson46.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,Long> {

    User findByUsername(String username);
}