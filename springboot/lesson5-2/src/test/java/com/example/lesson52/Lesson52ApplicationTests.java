package com.example.lesson52;

import com.example.lesson52.async.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Lesson52ApplicationTests {

    @Autowired
    private Task task;

    @Test
    public void contextLoads() {
        try {
            long start = System.currentTimeMillis();

            Future<String> task1 = task.doTaskOne();
            Future<String> task2 = task.doTaskTwo();
            Future<String> task3 = task.doTaskThree();

            while (true) {
                if (task1.isDone() && task2.isDone() && task3.isDone()) {
                    break;
                }
                Thread.sleep(1000);
            }

            long end = System.currentTimeMillis();

            System.out.println("任务全部完成，总耗时：" + (end - start) + "毫秒");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
