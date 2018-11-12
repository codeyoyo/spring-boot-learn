package com.example.lesson21.controller;

import com.example.lesson21.entity.BlogProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private BlogProperties blogProperties;

    @RequestMapping(value = "/hello")
    @ResponseBody
    public String index() {
        return "name" + blogProperties.getName() + ",title" + blogProperties.getTitle();
    }
}
