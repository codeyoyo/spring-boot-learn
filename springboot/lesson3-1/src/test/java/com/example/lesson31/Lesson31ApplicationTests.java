package com.example.lesson31;

import com.example.lesson31.web.HelloController;
import com.example.lesson31.web.UserController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
public class Lesson31ApplicationTests {

    private MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new HelloController(), new UserController()).build();
    }

    @Test
    public void getHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().string(equalTo("Hello World")));
    }

    @Test
    public void testUserController() {
        try {
            RequestBuilder request = null;

            // 1、get查一下user列表，应该为空
            request=get("/user/");
            mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("[]")));

            //2、post提交一个user
            request=post("/user/add/").param("id", String.valueOf(1)).param("name","测试大师").param("age","20");
            mvc.perform(request).andDo(MockMvcResultHandlers.print()).andExpect(content().string(equalTo("success")));

            //3.get获取user列表，应该有刚才插入的数据
            request=get("/user/");
            mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("[{\"id\":1,\"name\":\"测试大师\",\"age\":20}]")));

            //4.get一个id为1的user
            request=get("/user/1");
            mvc.perform(request).andExpect(content().string(equalTo("{\"id\":1,\"name\":\"测试终极大师\",\"age\":30}")));

            //5.del删除id为1的user
            request=post("/user/del/").param("id","1");
            mvc.perform(request).andExpect(content().string(equalTo("success")));


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
