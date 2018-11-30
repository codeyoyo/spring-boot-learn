package com.example.lesson41.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create(String name, Integer age) {
        String sql=String.format("insert into USER (name ,age) value (%s,%s)",name,age);
        jdbcTemplate.execute(sql);
    }

    @Override
    public Integer getAllUsers() {
        return jdbcTemplate.queryForObject("select count(0) from USER",Integer.class);
    }

    @Override
    public void deleteByName(String name) {
        jdbcTemplate.update("delete from USER where name =?",name);
    }

    @Override
    public void delelteAllUsers() {
        jdbcTemplate.execute("delete from user ");
    }
}
