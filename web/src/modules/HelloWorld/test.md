```
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
```