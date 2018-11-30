package com.example.lesson47;

import com.example.lesson47.domain.User;
import com.example.lesson47.domain.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Lesson47ApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    @Rollback
    public void contextLoads() {
        userMapper.insert("AAA",20);
        User user=userMapper.findByName("AAA");
        Assert.assertEquals(20,user.getAge().intValue());
    }

}
