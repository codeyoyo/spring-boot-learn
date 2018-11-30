package com.example.lesson46;

import com.example.lesson46.domain.User;
import com.example.lesson46.domain.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Lesson46ApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp(){
        userRepository.deleteAll();
    }

    @Test
    public void contextLoads() {

        // 创建三个User，并验证User总数
        userRepository.save(new User(1L, "didi", 30));
        userRepository.save(new User(2L, "mama", 40));
        userRepository.save(new User(3L, "kaka", 50));
        Assert.assertEquals(3, userRepository.findAll().size());

        // 删除一个User，再验证User总数
        Optional<User> u = userRepository.findById(1L);
        userRepository.delete(u.get());
        Assert.assertEquals(2, userRepository.findAll().size());

        // 删除一个User，再验证User总数
        u = Optional.ofNullable(userRepository.findByUsername("mama"));
        userRepository.delete(u.get());
        Assert.assertEquals(1, userRepository.findAll().size());
    }

}
