package com.example.lesson21;

import com.example.lesson21.entity.BlogProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Lesson21ApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private BlogProperties blogProperties;

    @Test
    public void HelloTest() {
        System.out.println("name:" + blogProperties.getName() + ",title:" + blogProperties.getTitle());
        System.out.println(blogProperties.getDesc());
        System.out.println(blogProperties.getValue());
        System.out.println(blogProperties.getNumber());
        System.out.println(blogProperties.getBignumber());
        System.out.println(blogProperties.getTest1());
        System.out.println(blogProperties.getTest2());
    }
}
