package com.example.lesson55;

import com.example.lesson55.async.Task;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Lesson55ApplicationTests {

    @Autowired
    private Task task;

    @Test
    public void contextLoads() {
        try {
            Future<String> futureResult = task.run();
            String result = futureResult.get(5,TimeUnit.SECONDS);
            log.info(result);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
