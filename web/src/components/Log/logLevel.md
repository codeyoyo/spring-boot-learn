之前介绍了在[《Spring boot中使用log4j记录日志》](#/log/log4j)，仅通过``log4j.properties``对日志级别进行控制，对于需要多环境部署的环境不是很方便，可能我们在开发环境大部分模块需要采用DEBUG级别，在测试环境可能需要小部分采用DEBUG级别，而在生产环境时我们又希望采用INFO级别。这个时候，我们要自己手工编辑``log4j.properties``文件来调整日志级别，不论在版本库中默认保存哪个环境的级别设定，都会增加其他环境使用人员的工作量，虽然很细微，但是手工修改总不是一件很好的选择，难免会发现修改后误提交等问题。

那么，有没有办法对于开发人员、运维人员都不需要改变源代码实现不同环境的不同日志级别呢?

是否还记得之前在[《Spring Boot属性配置文件详解》](#/config/ConfigIntroduce)一文中，提到的关于Spring Boot多环境的配置以及属性文件中的参数引用？若没有了解过相关内容，建议先阅读该文后继续此篇内容。

## 尝试改造

先以``lesson6-1``工程作为基础工程，我们来进行多环境配置的改造。

* 创建多环境配置文件
    * application-dev.properties：开发环境
    * application-test.properties：测试环境
    * application-prod.properties：生产环境
* application.properties中添加属性：spring.profiles.active=dev（默认激活application-dev.properties配置）
* application-dev.properties和application-test.properties配置文件中添加日志级别定义：logging.level.com.didispace=DEBUG
* application-prod.properties配置文件中添加日志级别定义：logging.level.com.didispace=INFO

通过上面的定义，根据``logging.level.com.didispace``在不同环境的配置文件中定义了不同的级别，但是我们已经把日志交给了log4j管理，看看我们log4j.properties中对com.didispace包下的日志定义是这样的，固定定义了DEBUG级别，并输出到名为didifile定义的appender中。

```
# LOG4J配置
log4j.category.com.didispace=DEBUG, lesson62

# com.didispace下的日志输出
log4j.appender.lesson62=org.apache.log4j.DailyRollingFileAppender
log4j.appender.lesson62.file=logs/my.log
log4j.appender.lesson62.DatePattern='.'yyyy-MM-dd
log4j.appender.lesson62.layout=org.apache.log4j.PatternLayout
log4j.appender.lesson62.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss,SSS} %5p %c{1}:%L ---- %m%n
```

那么，要如何动态的改变这个DEBUG级别呢？在[《Spring boot中使用log4j记录日志》](#/log/log4j)中还提到了关于配置文件中参数的引用。我们需要将DEBUG替换成application-{profile}.properties配置文件中定义logging.level.com.didispace即可，所以配置变为如下内容：

```
# LOG4J配置
log4j.category.com.didispace=${logging.level.com.lesson62}, lesson62

# com.didispace下的日志输出
log4j.appender.lesson62=org.apache.log4j.DailyRollingFileAppender
log4j.appender.lesson62.file=logs/my.log
log4j.appender.lesson62.DatePattern='.'yyyy-MM-dd
log4j.appender.lesson62.layout=org.apache.log4j.PatternLayout
log4j.appender.lesson62.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss,SSS} %5p %c{1}:%L ---- %m%n
```

到这里我们已经完成了所有配置工作，我们可以通过运行单元测试，然后看my.log文件中输出的日志内容。通过修改默认的``application-dev.properties``配置的日志级别为INFO，再运行单元测试的DEBUG内容是否被输出到了my.log中验证参数是否被正确引用了。

对于不同环境的使用人员也不需要改变代码或打包文件，只需要通过执行命令中参加参数即可，比如我想采用生产环境的级别，那么我可以这样运行应用：

```
java -jar xxx.jar --spring.profiles.active=prod
```

[完整示例：lesson6-2](https://github.com/codeyoyo/spring-boot-learn/tree/master/springboot/lesson6-2)