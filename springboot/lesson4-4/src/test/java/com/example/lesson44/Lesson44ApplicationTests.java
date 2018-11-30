package com.example.lesson44;

import com.example.lesson44.primary.User;
import com.example.lesson44.primary.UserRepository;
import com.example.lesson44.secondary.Message;
import com.example.lesson44.secondary.MessageRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.lang.reflect.Method;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Lesson44ApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Before
    public void setUp(){
        userRepository.deleteAll();
        messageRepository.deleteAll();
    }

    @Test
    public void contextLoads() {

        userRepository.save(new User("aaa",10));
        userRepository.save(new User("bbb",20));
        userRepository.save(new User("ccc",30));

        Assert.assertEquals(3,userRepository.findAll().size());

        messageRepository.save(new Message("01","aaaaaa"));

        Assert.assertEquals(1,messageRepository.findAll().size());
    }
}
