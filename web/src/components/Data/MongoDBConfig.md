## spring-boot-starter-mongodb-plus

``spring-boot-starter-mongodb-plus``是一名国内的大神开发的，就是用于扩展官方spring boot starter对mongodb的支持，提供更多配置属性，比如：连接数的配置等。

先来看看如果使用这个扩展，是否要比之前那样自己定制要方便的多：

### 如何使用

1. 在使用了``spring-boot-starter-data-mongodb``的项目中，增加以下依赖

```
<dependency>
    <groupId>com.spring4all</groupId>
    <artifactId>mongodb-plus-spring-boot-starter</artifactId>
</dependency>
```

1. 在应用主类上增加@EnableMongoPlus注解，比如：

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

### 后记

[spring-boot-starter-mongodb-plus的GitHub项目地址请求点击查看](https://github.com/SpringForAll/spring-boot-starter-mongodb-plus/)

[完整示例：lesson4-11](https://github.com/codeyoyo/spring-boot-learn/tree/master/springboot/lesson4-11)