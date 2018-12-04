在Spring Boot 2.0中推出了Relaxed Binding 2.0，对原有的属性绑定功能做了非常多的改进以帮助我们更容易的在Spring应用中加载和读取配置信息。下面本文就来说说Spring Boot 2.0中对配置的改进。
## 配置文件绑定
### 简单类型
在Spring Boot 2.0中对配置属性加载的时候会除了像1.x版本时候那样移除特殊字符外，还会将配置均以全小写的方式进行匹配和加载。所以，下面的4种配置方式都是等价的：

* properties格式：
```
spring.jpa.databaseplatform=mysql
spring.jpa.database-platform=mysql
spring.jpa.databasePlatform=mysql
spring.JPA.database_platform=mysql
```
* yaml格式：
```
spring:
  jpa:
    databaseplatform: mysql
    database-platform: mysql
    databasePlatform: mysql
    database_platform: mysql
```
**Tips：推荐使用全小写配合**``-``**分隔符的方式来配置，比如：**``spring.jpa.database-platform=mysql``
### List类型
在properties文件中使用``[]``来定位列表类型，比如：
```
spring.my-example.url[0]=http://example.com
spring.my-example.url[1]=http://spring.io
```
也支持使用**逗号**分割的配置方式，上面与下面的配置是等价的：
```
spring.my-example.url=http://example.com,http://spring.io
```
而在yaml文件中使用可以使用如下配置：
```
spring:
  my-example:
    url:
      - http://example.com
      - http://spring.io
```
也支持**逗号**分割的方式：
```
spring:
  my-example:
    url: http://example.com, http://spring.io
```
使用yaml文件配置先引用以下maven库：
```
<!-- 支持识别yml配置 -->
<dependency>  
  <groupId>com.fasterxml.jackson.dataformat</groupId>
  <artifactId>jackson-dataformat-yaml</artifactId>
</dependency>
```
**注意：在Spring Boot 2.0中对于List类型的配置必须是连续的，不然会抛出**``UnboundConfigurationPropertiesException``**异常，所以如下配置是不允许的：**
```
foo[0]=a
foo[2]=b
```
***在Spring Boot 1.x中上述配置是可以的，``foo[1]``**由于没有配置，它的值会是**``null``
### Map类型
Map类型在properties和yaml中的标准配置方式如下：
* properties格式：
```
spring.my-example.foo=bar
spring.my-example.hello=world
```
* yaml格式：
```
spring:
  my-example:
    foo: bar
    hello: world
```
**注意：如果Map类型的key包含非字母数字和``-``的字符，需要用``[]``括起来，比如：**
```
spring:
  my-example:
    '[foo.baz]': bar
```
## 环境属性绑定
__简单类型__  

在环境变量中通过小写转换与.替换``_``来映射配置文件中的内容，比如：环境变量``SPRING_JPA_DATABASEPLATFORM=mysql``的配置会产生与在配置文件中设置``spring.jpa.databaseplatform=mysql``一样的效果。

__List类型__  

由于环境变量中无法使用``[``和``]``符号，所以使用_来替代。任何由下划线包围的数字都会被认为是``[]``的数组形式。比如：
```
MY_FOO_1_ = my.foo[1]
MY_FOO_1_BAR = my.foo[1].bar
MY_FOO_1_2_ = my.foo[1][2]
```
另外，最后环境变量最后是以数字和下划线结尾的话，最后的下划线可以省略，比如上面例子中的第一条和第三条等价于下面的配置：
```
MY_FOO_1 = my.foo[1]
MY_FOO_1_2 = my.foo[1][2]
```
## 系统属性绑定  

__简单类型__

系统属性与文件配置中的类似，都以移除特殊字符并转化小写后实现绑定，比如下面的命令行参数都会实现配置``spring.jpa.databaseplatform=mysql``的效果：
```
-Dspring.jpa.database-platform=mysql
-Dspring.jpa.databasePlatform=mysql
-Dspring.JPA.database_platform=mysql
```
__List类型__

系统属性的绑定也与文件属性的绑定类似，通过``[]``来标示，比如：

```
-D"spring.my-example.url[0]=http://example.com"
-D"spring.my-example.url[1]=http://spring.io"
```

同样的，他也支持逗号分割的方式，比如：

```
-Dspring.my-example.url=http://example.com,http://spring.io
```
## 属性的读取
上文介绍了Spring Boot 2.0中对属性绑定的内容，可以看到对于一个属性我们可以有多种不同的表达，但是如果我们要在Spring应用程序的environment中读取属性的时候，每个属性的唯一名称符合如下规则：
* 通过``.``分离各个元素
* 最后一个``.``将前缀与属性名称分开
* 必须是字母（a-z）和数字(0-9)
* 必须是小写字母
* 用连字符``-``来分隔单词
* 唯一允许的其他字符是``[``和``]``，用于List的索引
* 不能以数字开头
所以，如果我们要读取配置文件中``spring.jpa.database-platform``的配置，可以这样写：
```
this.environment.containsProperty("spring.jpa.database-platform")
```
而下面的方式是无法获取到``spring.jpa.database-platform``配置内容的：
```
this.environment.containsProperty("spring.jpa.databasePlatform")
```
**注意：使用``@Value``获取配置内容的时候也需要这样的特点**
## 全新的绑定API
在Spring Boot 2.0中增加了新的绑定API来帮助我们更容易的获取配置信息。下面举个例子来帮助大家更容易的理解：
### 例子一：简单类型
假设在propertes配置中有这样一个配置：``com.didispace.foo=bar``
我们为它创建对应的配置类：
```
@Data
@ConfigurationProperties(prefix = "com.didispace")
public class FooProperties {

    private String foo;

}
```
接下来，通过最新的``Binder``就可以这样来拿配置信息了：
```
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);

        Binder binder = Binder.get(context.getEnvironment());

        // 绑定简单配置
        FooProperties foo = binder.bind("com.didispace", Bindable.of(FooProperties.class)).get();
        System.out.println(foo.getFoo());
    }
}
```
### 例子二：List类型
如果配置内容是List类型呢？比如：
```
com.didispace.post[0]=Why Spring Boot
com.didispace.post[1]=Why Spring Cloud

com.didispace.posts[0].title=Why Spring Boot
com.didispace.posts[0].content=It is perfect!
com.didispace.posts[1].title=Why Spring Cloud
com.didispace.posts[1].content=It is perfect too!
```
要获取这些配置依然很简单，可以这样实现：
```
ApplicationContext context = SpringApplication.run(Application.class, args);

Binder binder = Binder.get(context.getEnvironment());

// 绑定List配置
List<String> post = binder.bind("com.didispace.post", Bindable.listOf(String.class)).get();
System.out.println(post);

List<PostInfo> posts = binder.bind("com.didispace.posts", Bindable.listOf(PostInfo.class)).get();
System.out.println(posts);
```

[完整示列：lesson2-2](https://github.com/codeyoyo/spring-boot-learn/tree/master/springboot/lesson2-2)