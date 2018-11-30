package com.example.lesson21.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BlogProperties {
    @Value("${com.lesson.name}")
    private String name;
    @Value("${com.lesson.title}")
    private String title;
    @Value("${com.lesson.desc}")
    private String desc;
    @Value("${com.lesson.value}")
    private String value;
    @Value("${com.lesson.number}")
    private String number;
    @Value("${com.lesson.bignumber}")
    private String bignumber;
    @Value("${com.lesson.test1}")
    private String test1;
    @Value("${com.lesson.test2}")
    private String test2;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBignumber() {
        return bignumber;
    }

    public void setBignumber(String bignumber) {
        this.bignumber = bignumber;
    }

    public String getTest1() {
        return test1;
    }

    public void setTest1(String test1) {
        this.test1 = test1;
    }

    public String getTest2() {
        return test2;
    }

    public void setTest2(String test2) {
        this.test2 = test2;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
