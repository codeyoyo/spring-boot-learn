package com.example.lesson49.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create(String name, Integer age) {
        jdbcTemplate.execute(String.format("insert into user(`name`,`age`) values ('%s','%s')",name,age));
    }

    @Override
    public void deleteByName(String name) {
        jdbcTemplate.execute(String.format("delete from user where name='%s'",name));
    }

    @Override
    public Integer getAllUsers() {
        return jdbcTemplate.queryForObject("select count(0) from user",Integer.class);
    }

    @Override
    public void deleteAllUsers() {
        jdbcTemplate.execute("delete from user");
    }
}
