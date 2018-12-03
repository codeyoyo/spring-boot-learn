之前在Spring Boot中整合MyBatis时，采用了注解的配置方式，相信很多人还是比较喜欢这种优雅的方式的，也收到不少读者朋友的反馈和问题，主要集中于针对各种场景下注解如何使用，下面就对几种常见的情况举例说明用法。

在做下面的示例之前，先准备一个整合好MyBatis的工程，可参见[Spring Boot整合MyBatis](/Data/MyBatis)，也可直接使用整合好的样例：[Chapter3-2-7](Chapter3-2-7)。

## 传参方式

下面通过几种不同传参方式来实现前文中实现的插入操作。

### 使用@Param

在之前的整合示例中我们已经使用了这种最简单的传参方式，如下：

```
@Insert("INSERT INTO USER(NAME, AGE) VALUES(#{name}, #{age})")
int insert(@Param("name") String name, @Param("age") Integer age);
```

这种方式很好理解，``@Param``中定义的``name``对应了SQL中的``#{name}``，``age``对应了SQL中的``#{age}``。

### 使用Map

如下代码，通过Map对象来作为传递参数的容器：

```
@Insert("INSERT INTO USER(NAME, AGE) VALUES(#{name,jdbcType=VARCHAR}, #{age,jdbcType=INTEGER})")
int insertByMap(Map<String, Object> map);
```

对于Insert语句中需要的参数，我们只需要在map中填入同名的内容即可，具体如下面代码所示：

```
Map<String, Object> map = new HashMap<>();
map.put("name", "CCC");
map.put("age", 40);
userMapper.insertByMap(map);
```

### 使用对象

除了Map对象，我们也可直接使用普通的Java对象来作为查询条件的传参，比如我们可以直接使用User对象:

```
@Insert("INSERT INTO USER(NAME, AGE) VALUES(#{name}, #{age})")
int insertByUser(User user);
```

这样语句中的``#{name}``、``#{age}``就分别对应了User对象中的``name``和``age``属性。

## 增删改查

MyBatis针对不同的数据库操作分别提供了不同的注解来进行配置，在之前的示例中演示了``@Insert``，下面针对User表做一组最基本的增删改查作为示例：

```
public interface UserMapper {

    @Select("SELECT * FROM user WHERE name = #{name}")
    User findByName(@Param("name") String name);

    @Insert("INSERT INTO user(name, age) VALUES(#{name}, #{age})")
    int insert(@Param("name") String name, @Param("age") Integer age);

    @Update("UPDATE user SET age=#{age} WHERE name=#{name}")
    void update(User user);

    @Delete("DELETE FROM user WHERE id =#{id}")
    void delete(Long id);
}
```

在完成了一套增删改查后，不妨我们试试下面的单元测试来验证上面操作的正确性：

```
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class ApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Test
	@Rollback
	public void testUserMapper() throws Exception {
		// insert一条数据，并select出来验证
		userMapper.insert("AAA", 20);
		User u = userMapper.findByName("AAA");
		Assert.assertEquals(20, u.getAge().intValue());
		// update一条数据，并select出来验证
		u.setAge(30);
		userMapper.update(u);
		u = userMapper.findByName("AAA");
		Assert.assertEquals(30, u.getAge().intValue());
		// 删除这条数据，并select验证
		userMapper.delete(u.getId());
		u = userMapper.findByName("AAA");
		Assert.assertEquals(null, u);
	}
}
```

## 返回结果的绑定

对于增、删、改操作相对变化较小。而对于“查”操作，我们往往需要进行多表关联，汇总计算等操作，那么对于查询的结果往往就不再是简单的实体对象了，往往需要返回一个与数据库实体不同的包装类，那么对于这类情况，就可以通过``@Results``和``@Result``注解来进行绑定，具体如下：

```
@Results({
    @Result(property = "name", column = "name"),
    @Result(property = "age", column = "age")
})
@Select("SELECT name, age FROM user")
List<User> findAll();
```

在上面代码中，@Result中的property属性对应User对象中的成员名，column对应SELECT出的字段名。在该配置中故意没有查出id属性，只对User对应中的name和age对象做了映射配置，这样可以通过下面的单元测试来验证查出的id为null，而其他属性不为null：

```
@Test
@Rollback
public void testUserMapper() throws Exception {
	List<User> userList = userMapper.findAll();
	for(User user : userList) {
		Assert.assertEquals(null, user.getId());
		Assert.assertNotEquals(null, user.getName());
	}
}
```
## Spring Boot中增强对MongoDB的配置（连接池等）

### spring-boot-starter-mongodb-plus使用

1. 在使用了``spring-boot-starter-data-mongodb``的项目中，增加以下依赖

```
<dependency>
    <groupId>com.spring4all</groupId>
    <artifactId>mongodb-plus-spring-boot-starter</artifactId>
    <version>1.0.0.RELEASE</version>
</dependency>
```

1. 在应用主类上增加``@EnableMongoPlus``注解，比如：

```
@EnableMongoPlus
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```

### 可用配置参数

可配置参数如下：

```
spring.data.mongodb.option.min-connection-per-host=0
spring.data.mongodb.option.max-connection-per-host=100
spring.data.mongodb.option.threads-allowed-to-block-for-connection-multiplier=5
spring.data.mongodb.option.server-selection-timeout=30000
spring.data.mongodb.option.max-wait-time=120000
spring.data.mongodb.option.max-connection-idle-time=0
spring.data.mongodb.option.max-connection-life-time=0
spring.data.mongodb.option.connect-timeout=10000
spring.data.mongodb.option.socket-timeout=0

spring.data.mongodb.option.socket-keep-alive=false
spring.data.mongodb.option.ssl-enabled=false
spring.data.mongodb.option.ssl-invalid-host-name-allowed=false
spring.data.mongodb.option.always-use-m-beans=false

spring.data.mongodb.option.heartbeat-socket-timeout=20000
spring.data.mongodb.option.heartbeat-connect-timeout=20000
spring.data.mongodb.option.min-heartbeat-frequency=500
spring.data.mongodb.option.heartbeat-frequency=10000
spring.data.mongodb.option.local-threshold=15
```

*上述配置值均为默认值*

## 后记

本文主要介绍几种最为常用的方式，更多其他注解的使用可参见文档：
[http://www.mybatis.org/mybatis-3/zh/java-api.html](http://www.mybatis.org/mybatis-3/zh/java-api.html)

[完整示例：lesson4-8](https://github.com/codeyoyo/spring-boot-learn/tree/master/springboot/lesson4-8)