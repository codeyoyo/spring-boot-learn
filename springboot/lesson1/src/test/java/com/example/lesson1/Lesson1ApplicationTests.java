package com.example.lesson1;

import com.example.lesson1.controller.HelloController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Lesson1ApplicationTests {

	private MockMvc mvc;

	@Before
	public void setUp(){
		try{
			mvc=MockMvcBuilders.standaloneSetup(new HelloController()).build();
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}

	@Test
	public void contextLoads() {
		try {
			mvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().string(equalTo("Hello World")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
