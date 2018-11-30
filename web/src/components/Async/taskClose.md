> 上周发了一篇关于Spring Boot中使用``@Async``来实现异步任务和线程池控制的文章：[《Spring Boot使用@Async实现异步调用：自定义线程池》](#/async/taskExecutor)。由于最近身边也发现了不少异步任务没有正确处理而导致的不少问题，所以在本文就接前面内容，继续说说线程池的优雅关闭，主要针对``ThreadPoolTaskScheduler``线程池。

## 问题现象

在上篇文章的例子[lesson5-3]中，我们定义了一个线程池，然后利用``@Async``注解写了3个任务，并指定了这些任务执行使用的线程池。在上文的单元测试中，我们没有具体说说shutdown相关的问题，下面我们就来模拟一个问题现场出来。

第一步：如前文一样，我们定义一个``ThreadPoolTaskScheduler``线程池：

```
@EnableAsync
@Configuration
public class TaskPoolConfig {

    @Bean("taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(20);
        executor.setThreadNamePrefix("taskExecutor-");

        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        return executor;
    }
}
```

第二步：改造之前的异步任务，让它依赖一个外部资源，比如：Redis

```
@Slf4j
@Component
public class Task {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Async("taskExecutor")
    public void doTaskOne() throws Exception {
        log.info("开始做任务一");
        long start = System.currentTimeMillis();
        log.info(stringRedisTemplate.randomKey());
        long end = System.currentTimeMillis();
        log.info("完成任务一，耗时：" + (end - start) + "毫秒");
    }

    @Async("taskExecutor")
    public void doTaskTwo() throws Exception {
        log.info("开始做任务二");
        long start = System.currentTimeMillis();
        log.info(stringRedisTemplate.randomKey());
        long end = System.currentTimeMillis();
        log.info("完成任务二，耗时：" + (end - start) + "毫秒");
    }

    @Async("taskExecutor")
    public void doTaskThree() throws Exception {
        log.info("开始做任务三");
        long start = System.currentTimeMillis();
        log.info(stringRedisTemplate.randomKey());
        long end = System.currentTimeMillis();
        log.info("完成任务三，耗时：" + (end - start) + "毫秒");
    }
}
```

__注意：这里省略了pom.xml中引入依赖和配置redis的步骤__

第三步：修改单元测试，模拟高并发情况下ShutDown的情况：

```
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private Task task;

    @Test
    @SneakyThrows
    public void test() {

        for (int i = 0; i < 10000; i++) {
            task.doTaskOne();
            task.doTaskTwo();
            task.doTaskThree();

            if (i == 9999) {
                System.exit(0);
            }
        }
    }
}
```

说明：通过for循环往上面定义的线程池中提交任务，由于是异步执行，在执行过程中，利用``System.exit(0)``来关闭程序，此时由于有任务在执行，就可以观察这些异步任务的销毁与Spring容器中其他资源的顺序是否安全。

第四步：运行上面的单元测试，我们将碰到下面的异常内容。

```
org.springframework.data.redis.RedisConnectionFailureException: Cannot get Jedis connection; nested exception is redis.clients.jedis.exceptions.JedisConnectionException: Could not get a resource from the pool
	at org.springframework.data.redis.connection.jedis.JedisConnectionFactory.fetchJedisConnector(JedisConnectionFactory.java:204) ~[spring-data-redis-1.8.10.RELEASE.jar:na]
	at org.springframework.data.redis.connection.jedis.JedisConnectionFactory.getConnection(JedisConnectionFactory.java:348) ~[spring-data-redis-1.8.10.RELEASE.jar:na]
	at org.springframework.data.redis.core.RedisConnectionUtils.doGetConnection(RedisConnectionUtils.java:129) ~[spring-data-redis-1.8.10.RELEASE.jar:na]
	at org.springframework.data.redis.core.RedisConnectionUtils.getConnection(RedisConnectionUtils.java:92) ~[spring-data-redis-1.8.10.RELEASE.jar:na]
	at org.springframework.data.redis.core.RedisConnectionUtils.getConnection(RedisConnectionUtils.java:79) ~[spring-data-redis-1.8.10.RELEASE.jar:na]
	at org.springframework.data.redis.core.RedisTemplate.execute(RedisTemplate.java:194) ~[spring-data-redis-1.8.10.RELEASE.jar:na]
	at org.springframework.data.redis.core.RedisTemplate.execute(RedisTemplate.java:169) ~[spring-data-redis-1.8.10.RELEASE.jar:na]
	at org.springframework.data.redis.core.RedisTemplate.randomKey(RedisTemplate.java:781) ~[spring-data-redis-1.8.10.RELEASE.jar:na]
	at com.didispace.async.Task.doTaskOne(Task.java:26) ~[classes/:na]
	at com.didispace.async.Task$$FastClassBySpringCGLIB$$ca3ff9d6.invoke(<generated>) ~[classes/:na]
	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204) ~[spring-core-4.3.14.RELEASE.jar:4.3.14.RELEASE]
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:738) ~[spring-aop-4.3.14.RELEASE.jar:4.3.14.RELEASE]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:157) ~[spring-aop-4.3.14.RELEASE.jar:4.3.14.RELEASE]
	at org.springframework.aop.interceptor.AsyncExecutionInterceptor$1.call(AsyncExecutionInterceptor.java:115) ~[spring-aop-4.3.14.RELEASE.jar:4.3.14.RELEASE]
	at java.util.concurrent.FutureTask.run(FutureTask.java:266) [na:1.8.0_151]
	at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.access$201(ScheduledThreadPoolExecutor.java:180) [na:1.8.0_151]
	at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:293) [na:1.8.0_151]
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149) [na:1.8.0_151]
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624) [na:1.8.0_151]
	at java.lang.Thread.run(Thread.java:748) [na:1.8.0_151]
Caused by: redis.clients.jedis.exceptions.JedisConnectionException: Could not get a resource from the pool
	at redis.clients.util.Pool.getResource(Pool.java:53) ~[jedis-2.9.0.jar:na]
	at redis.clients.jedis.JedisPool.getResource(JedisPool.java:226) ~[jedis-2.9.0.jar:na]
	at redis.clients.jedis.JedisPool.getResource(JedisPool.java:16) ~[jedis-2.9.0.jar:na]
	at org.springframework.data.redis.connection.jedis.JedisConnectionFactory.fetchJedisConnector(JedisConnectionFactory.java:194) ~[spring-data-redis-1.8.10.RELEASE.jar:na]
	... 19 common frames omitted
Caused by: java.lang.InterruptedException: null
	at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.reportInterruptAfterWait(AbstractQueuedSynchronizer.java:2014) ~[na:1.8.0_151]
	at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.awaitNanos(AbstractQueuedSynchronizer.java:2088) ~[na:1.8.0_151]
	at org.apache.commons.pool2.impl.LinkedBlockingDeque.pollFirst(LinkedBlockingDeque.java:635) ~[commons-pool2-2.4.3.jar:2.4.3]
	at org.apache.commons.pool2.impl.GenericObjectPool.borrowObject(GenericObjectPool.java:442) ~[commons-pool2-2.4.3.jar:2.4.3]
	at org.apache.commons.pool2.impl.GenericObjectPool.borrowObject(GenericObjectPool.java:361) ~[commons-pool2-2.4.3.jar:2.4.3]
	at redis.clients.util.Pool.getResource(Pool.java:49) ~[jedis-2.9.0.jar:na]
	... 22 common frames omitted
```

## 如何解决

### 原因分析

从异常信息``JedisConnectionException: Could not get a resource from the pool``来看，我们很容易的可以想到，在应用关闭的时候异步任务还在执行，由于Redis连接池先销毁了，导致异步任务中要访问Redis的操作就报了上面的错。所以，我们得出结论，上面的实现方式在应用关闭的时候是不优雅的，那么我们要怎么做呢？

### 解决方法

要解决上面的问题很简单，Spring的``ThreadPoolTaskScheduler``为我们提供了相关的配置，只需要加入如下设置即可：

```
@Bean("taskExecutor")
public Executor taskExecutor() {
    ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
    executor.setPoolSize(20);
    executor.setThreadNamePrefix("taskExecutor-");
    executor.setWaitForTasksToCompleteOnShutdown(true);
    executor.setAwaitTerminationSeconds(60);
    return executor;
}
```

说明：``setWaitForTasksToCompleteOnShutdown（true）``该方法就是这里的关键，用来设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean，这样这些异步任务的销毁就会先于Redis线程池的销毁。同时，这里还设置了``setAwaitTerminationSeconds(60)``，该方法用来设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住。

[完整示例：lesson5-4](lesson5-4)