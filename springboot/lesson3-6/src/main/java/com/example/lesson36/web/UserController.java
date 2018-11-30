package com.example.lesson36.web;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @ResponseBody
    @PostMapping(value = "/user", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public User create(@RequestBody User user) {
        user.setName("spring boot learn" + user.getName());
        user.setAge(user.getAge() + 100);
        return user;
    }
}
