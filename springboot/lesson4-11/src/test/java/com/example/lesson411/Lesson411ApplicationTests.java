package com.example.lesson411;

import com.mongodb.MongoClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Lesson411ApplicationTests {

    @Autowired
    private MongoClient mongoClient;

    @Test
    public void contextLoads() {
        log.info("MinConnectionsPerHost = {}, MaxConnectionsPerHost = {}",
                mongoClient.getMongoClientOptions().getMinConnectionsPerHost(),
                mongoClient.getMongoClientOptions().getConnectionsPerHost());
    }

}
