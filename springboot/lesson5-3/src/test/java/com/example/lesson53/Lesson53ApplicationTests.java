package com.example.lesson53;

import com.example.lesson53.async.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Lesson53ApplicationTests {

    @Autowired
    private Task task;

    @Test
    public void contextLoads() throws InterruptedException {
        task.doTaskOne();;
        task.doTaskTwo();
        task.doTaskThree();

        Thread.currentThread().join();
    }

}
