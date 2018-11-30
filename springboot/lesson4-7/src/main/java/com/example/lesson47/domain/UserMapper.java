package com.example.lesson47.domain;

import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("select * from user where name=#{name} limit 1")
    User findByName(@Param("name") String name);

    @Insert("insert into user(`name`,`age`) value(#{name},#{age})")
    int insert(@Param("name") String name,@Param("age") Integer age);
}