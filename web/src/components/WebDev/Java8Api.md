> ``LocalDate``、``LocalTime``、``LocalDateTime``是Java 8开始提供的时间日期API，主要用来优化Java 8以前对于时间日期的处理操作。然而，我们在使用Spring Boot或使用Spring Cloud Feign的时候，往往会发现使用请求参数或返回结果中有``LocalDate``、``LocalTime``、``LocalDateTime``的时候会发生各种问题。本文我们就来说说这种情况下出现的问题，以及如何解决。

### 问题现象

先来看看症状。比如下面的例子：

```
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RestController
    class HelloController {

        @PostMapping("/user")
        public UserDto user(@RequestBody UserDto userDto) throws Exception {
            return userDto;
        }

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class UserDto {

        private String userName;
        private LocalDate birthday;

    }

}
```

上面的代码构建了一个简单的Spring Boot Web应用，它提供了一个提交用户信息的接口，用户信息中包含了``LocalDate``类型的数据。此时，如果我们使用Feign来调用这个接口的时候，会得到如下错误：

```
2018-03-13 09:22:58,445 WARN  [http-nio-9988-exec-3] org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver - Failed to read HTTP message: org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error: Can not construct instance of java.time.LocalDate: no suitable constructor found, can not deserialize from Object value (missing default constructor or creator, or perhaps need to add/enable type information?); nested exception is com.fasterxml.jackson.databind.JsonMappingException: Can not construct instance of java.time.LocalDate: no suitable constructor found, can not deserialize from Object value (missing default constructor or creator, or perhaps need to add/enable type information?)
 at [Source: java.io.PushbackInputStream@67064c65; line: 1, column: 63] (through reference chain: java.util.ArrayList[0]->com.didispace.UserDto["birthday"])
```

### 分析解决

对于上面的错误信息``JSON parse error: Can not construct instance of java.time.LocalDate: no suitable constructor found, can not deserialize from Object value``，熟悉Spring MVC的童鞋应该马上就能定位错误与``LocalDate``的反序列化有关。但是，依然会有很多读者会被这段错误信息``java.util.ArrayList[0]->com.didispace.UserDto["birthday"]``所困惑。我们命名提交的``UserDto["birthday"]``是个``LocalDate``对象嘛，跟``ArrayList``列表对象有啥关系呢？

我们不妨通过postman等手工发一个请求看看服务端返回的是什么？比如你可以按下图发起一个请求：

!(Spring-Boot-And-Feign-Use-localdate-1.png)[../../assets/Spring-Boot-And-Feign-Use-localdate-1.png]

从上图中我们就可以理解上面我所提到的困惑了，实际上默认情况下Spring MVC对于LocalDate序列化成了一个数组类型，而Feign在调用的时候，还是按照ArrayList来处理，所以自然无法反序列化为``LocalDate``对象了。

__解决方法__

为了解决上面的问题非常简单，因为jackson也为此提供了一整套的序列化方案，我们只需要在``pom.xml``中引入``jackson-datatype-jsr310``依赖，具体如下：

```
<dependency>
    <groupId>com.fasterxml.jackson.datatype</groupId>
    <artifactId>jackson-datatype-jsr310</artifactId>
</dependency>
```

*注意：在设置了spring boot的parent的情况下不需要指定具体的版本，也不建议指定某个具体版本*

在该模块中封装对Java 8的时间日期API序列化的实现，其具体实现在这个类中：``com.fasterxml.jackson.datatype.jsr310.JavaTimeModule``（注意：一些较早版本疯转在这个类中“``com.fasterxml.jackson.datatype.jsr310.JSR310Module``）。在配置了依赖之后，我们只需要在上面的应用主类中增加这个序列化模块，并禁用对日期以时间戳方式输出的特性：

```
@Bean
public ObjectMapper serializingObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper;
}
```

此时，我们在访问刚才的接口，就不再是数组类型了，同时对于Feign客户端的调用也不会再出现上面的错误了。

### 代码示例

本文的相关例子可以查看下面仓库中的``Chapter3-1-7``目录：

* [Github：https://github.com/dyc87112/SpringBoot-Learning](Github：https://github.com/dyc87112/SpringBoot-Learning)