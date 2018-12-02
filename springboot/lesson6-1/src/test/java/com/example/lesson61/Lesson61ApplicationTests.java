package com.example.lesson61;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.apache.log4j.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class Lesson61ApplicationTests {

    private Logger logger=Logger.getLogger(getClass());

    @Test
    public void contextLoads() {
        logger.info("输出info");
        logger.debug("输出debug");
        logger.error("输出error");
    }

}
