package com.example.lesson42;

import com.example.lesson42.domain.User;
import com.example.lesson42.domain.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Lesson42ApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void contextLoads() {

        userRepository.deleteAll();

        userRepository.save(new User("AAA",10));
        userRepository.save(new User("BBB",20));
        userRepository.save(new User("CCC",30));

        Assert.assertEquals(3,userRepository.findAll().size());

        Assert.assertEquals(10,userRepository.findUserByName("AAA").getAge().longValue());

        Assert.assertEquals(20,userRepository.findUser("BBB").getAge().longValue());

        Assert.assertEquals("CCC",userRepository.findUserByNameAndAge("CCC",30).getName());

        userRepository.delete(userRepository.findUserByName("AAA"));

        Assert.assertEquals(2,userRepository.findAll().size());
    }

}
