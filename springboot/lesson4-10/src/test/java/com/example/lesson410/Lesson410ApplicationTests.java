package com.example.lesson410;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Lesson410ApplicationTests {

    @Autowired
    PersonRepository personRepository;

    @Test
    public void findAll(){
        personRepository.findAll().forEach(p->{
            System.out.println(p);
        });
    }

    @Test
    public void contextLoads() {
        Person person=new Person();
        person.setUid("uid:1");
        person.setCommonName("AAA");
        person.setCommonName("aaa");
        person.setUserPassword("123456");
        personRepository.save(person);

        personRepository.findAll().forEach(p->{
            System.out.println(p);
        });
    }

}
