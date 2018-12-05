package com.example.lesson63;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.apache.log4j.Logger;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Lesson63ApplicationTests {

    private Logger logger = Logger.getLogger(getClass());

    @Test
    public void contextLoads() {
        logger.info("输出info");
        logger.debug("输出debug");
        logger.error("输出error");
    }

}
