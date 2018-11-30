package com.example.lesson412.service;

import com.example.lesson412.domain.User;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    User login(String name,String password);
}
