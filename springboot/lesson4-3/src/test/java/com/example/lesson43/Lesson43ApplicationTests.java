package com.example.lesson43;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Lesson43ApplicationTests {

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate primaryJdbcTemplate;

    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    private JdbcTemplate secondaryJdbcTemplate;

    @Before
    public void setUp(){
        primaryJdbcTemplate.execute("delete from user ");
        secondaryJdbcTemplate.execute("delete from user");
    }

    @Test
    public void contextLoads() {
        // 往第一个数据源中插入两条数据
        primaryJdbcTemplate.update("insert into user (`id`,`name`,`age`) values (?,?,?)",1,"aaa",20);
        primaryJdbcTemplate.update("insert into user (`id`,`name`,`age`) values (?,?,?)",2,"bbb",30);

        // 往第二个数据源中插入一条数据，若插入的是第一个数据源，则会主键冲突报错
        secondaryJdbcTemplate.update("insert into user(id,name,age) values(?, ?, ?)", 1, "aaa", 20);

        // 查一下第一个数据源中是否有两条数据，验证插入是否成功
        Assert.assertEquals("2", primaryJdbcTemplate.queryForObject("select count(1) from user", String.class));

        // 查一下第一个数据源中是否有两条数据，验证插入是否成功
        Assert.assertEquals("1", secondaryJdbcTemplate.queryForObject("select count(1) from user", String.class));

    }

}
