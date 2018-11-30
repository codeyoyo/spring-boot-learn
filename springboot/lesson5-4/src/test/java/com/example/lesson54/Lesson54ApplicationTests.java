package com.example.lesson54;

import com.example.lesson54.async.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Lesson54ApplicationTests {

    @Autowired
    private Task task;

    @Test
    public void contextLoads() {
        for (int i = 0; i < 10000; i++) {
            task.doTaskOne();
            task.doTaskTwo();
            task.doTaskThree();

            if (i == 9999) {
                System.exit(0);
                break;
            }
        }
    }

}
