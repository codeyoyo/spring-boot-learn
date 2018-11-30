> **今天继续来聊Spring Boot 2.0的新特性。本文将具体说说2.0版本中的事件模型，尤其是新增的事件：**``ApplicationStartedEvent``。

在Spring Boot 2.0中对事件模型做了一些增强，主要就是增加了``ApplicationStartedEvent``事件，所以在2.0版本中所有的事件按执行的先后顺序如下：

* ``ApplicationStartingEvent``
* ``ApplicationEnvironmentPreparedEvent``
* ``ApplicationPreparedEvent``
* ``ApplicationStartedEvent`` <= 新增的事件
* ``ApplicationReadyEvent``
* ``ApplicationFailedEvent``

从上面的列表中，我们可以看到``ApplicationStartedEvent``位于``ApplicationPreparedEvent``之后，``ApplicationReadyEvent``之前。

下面我们通过代码的方式来直观的感受这个事件的切入位置，以便与将来我们在这个切入点加入自己需要的逻辑。

第一步：我们可以编写``ApplicationPreparedEvent``、``ApplicationStartedEvent``以及``ApplicationReadyEvent``三个事件的监听器，然后在这三个事件触发的时候打印一些日志来观察它们各自的切入点，比如：

```
@Slf4j
public class ApplicationPreparedEventListener implements ApplicationListener<ApplicationPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationPreparedEvent event) {
        log.info("......ApplicationPreparedEvent......");
    }

}

@Slf4j
public class ApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        log.info("......ApplicationStartedEvent......");
    }

}

@Slf4j
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("......ApplicationReadyEvent......");
    }

}
```

第二步：在``/src/main/resources/``目录下新建：``META-INF/spring.factories``配置文件，通过配置``org.springframework.context.ApplicationListener``来加载上面我们编写的监听器。

```
org.springframework.context.ApplicationListener=
  com.didispace.ApplicationPreparedEventListener,\
  com.didispace.ApplicationReadyEventListener,\
  com.didispace.ApplicationStartedEventListener
```

此时，我们运行Spring Boot应用可以获得类似如下日志输出：

```
2018-03-07 18:15:18.591  INFO 83387 --- [           main] com.didispace.Application                : Starting Application on zhaiyongchaodeMacBook-Pro.local with PID 83387 (/Users/zhaiyongchao/Documents/git/github/SpringBoot-Learning/Chapter1-2-1/target/classes started by zhaiyongchao in /Users/zhaiyongchao/Documents/git/github/SpringBoot-Learning/Chapter1-2-1)
2018-03-07 18:15:18.591  INFO 83387 --- [           main] com.didispace.Application                : No active profile set, falling back to default profiles: default
2018-03-07 18:15:18.658  INFO 83387 --- [           main] c.d.ApplicationPreparedEventListener     : ......ApplicationPreparedEvent......
2018-03-07 18:15:18.662  INFO 83387 --- [           main] ConfigServletWebServerApplicationContext : Refreshing org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext@20d3d15a: startup date [Wed Mar 07 18:15:18 CST 2018]; root of context hierarchy
2018-03-07 18:15:19.879  INFO 83387 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2018-03-07 18:15:19.926  INFO 83387 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2018-03-07 18:15:19.930  INFO 83387 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet Engine: Apache Tomcat/8.5.28
2018-03-07 18:15:19.946  INFO 83387 --- [ost-startStop-1] o.a.catalina.core.AprLifecycleListener   : The APR based Apache Tomcat Native library which allows optimal performance in production environments was not found on the java.library.path: [/Users/zhaiyongchao/Library/Java/Extensions:/Library/Java/Extensions:/Network/Library/Java/Extensions:/System/Library/Java/Extensions:/usr/lib/java:.]
2018-03-07 18:15:20.068  INFO 83387 --- [ost-startStop-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2018-03-07 18:15:20.068  INFO 83387 --- [ost-startStop-1] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 1410 ms
2018-03-07 18:15:20.210  INFO 83387 --- [ost-startStop-1] o.s.b.w.servlet.ServletRegistrationBean  : Servlet dispatcherServlet mapped to [/]
2018-03-07 18:15:20.214  INFO 83387 --- [ost-startStop-1] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'characterEncodingFilter' to: [/*]
2018-03-07 18:15:20.214  INFO 83387 --- [ost-startStop-1] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'hiddenHttpMethodFilter' to: [/*]
2018-03-07 18:15:20.214  INFO 83387 --- [ost-startStop-1] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'httpPutFormContentFilter' to: [/*]
2018-03-07 18:15:20.215  INFO 83387 --- [ost-startStop-1] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'requestContextFilter' to: [/*]
2018-03-07 18:15:20.513  INFO 83387 --- [           main] s.w.s.m.m.a.RequestMappingHandlerAdapter : Looking for @ControllerAdvice: org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext@20d3d15a: startup date [Wed Mar 07 18:15:18 CST 2018]; root of context hierarchy
2018-03-07 18:15:20.592  INFO 83387 --- [           main] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped "{[/error]}" onto public org.springframework.http.ResponseEntity<java.util.Map<java.lang.String, java.lang.Object>> org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController.error(javax.servlet.http.HttpServletRequest)
2018-03-07 18:15:20.593  INFO 83387 --- [           main] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped "{[/error],produces=[text/html]}" onto public org.springframework.web.servlet.ModelAndView org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController.errorHtml(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse)
2018-03-07 18:15:20.623  INFO 83387 --- [           main] o.s.w.s.handler.SimpleUrlHandlerMapping  : Mapped URL path [/webjars/**] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2018-03-07 18:15:20.623  INFO 83387 --- [           main] o.s.w.s.handler.SimpleUrlHandlerMapping  : Mapped URL path [/**] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2018-03-07 18:15:20.660  INFO 83387 --- [           main] o.s.w.s.handler.SimpleUrlHandlerMapping  : Mapped URL path [/**/favicon.ico] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2018-03-07 18:15:20.787  INFO 83387 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
2018-03-07 18:15:20.839  INFO 83387 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2018-03-07 18:15:20.843  INFO 83387 --- [           main] com.didispace.Application                : Started Application in 2.866 seconds (JVM running for 3.337)
2018-03-07 18:15:20.845  INFO 83387 --- [           main] c.d.ApplicationStartedEventListener      : ......ApplicationStartedEvent......
2018-03-07 18:15:20.847  INFO 83387 --- [           main] c.d.ApplicationReadyEventListener        : ......ApplicationReadyEvent......
```

从日志中我们可以看到清晰的看到``ApplicationPreparedEvent``、``ApplicationStartedEvent``以及``ApplicationReadyEvent``三个事件的切入点。通过这个例子可能读者会感到疑问：``ApplicationStartedEvent``和``ApplicationReadyEvent``从事件命名和日志输出位置来看，都是应用加载完成之后的事件，它们是否有什么区别呢？

下面可以看看官方文档对``ApplicationStartedEvent``和``ApplicationReadyEvent``的解释：  

> An ApplicationStartedEvent is sent after the context has been refreshed but before any application and command-line runners have been called.An ApplicationReadyEvent is sent after any application and command-line runners have been called. It indicates that the application is ready to service requests

从文档中我们可以知道他们两中间还有一个过程就是``command-line runners``被调用的内容。所以，为了更准确的感受这两个事件的区别，我们在应用主类中加入``CommandLineRunner``的实现，比如：

```
@Slf4j
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public DataLoader dataLoader() {
        return new DataLoader();
    }

    @Slf4j
    static class DataLoader implements CommandLineRunner {

        @Override
        public void run(String... strings) throws Exception {
            log.info("Loading data...");
        }
    }

}
```

最后，我们再运行程序，此时我们可以发现这两个事件中间输出了上面定义的DataLoader的输出内容，具体如下：

```
2018-03-07 18:15:20.845  INFO 83387 --- [main] c.d.ApplicationStartedEventListener      : ......ApplicationStartedEvent......
2018-03-07 18:15:20.846  INFO 83387 --- [main] com.didispace.Application$DataLoader     : Loading data...
2018-03-07 18:15:20.847  INFO 83387 --- [main] c.d.ApplicationReadyEventListener        : ......ApplicationReadyEvent......
```

## 代码示例

本文的相关例子可以查看下面仓库中的``Chapter1-2-1``目录：

* [Github：https://github.com/dyc87112/SpringBoot-Learning](Github：https://github.com/dyc87112/SpringBoot-Learning)