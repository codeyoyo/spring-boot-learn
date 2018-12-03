之前连续写了几篇关于使用``@Async``实现异步调用的内容，也得到不少童鞋的反馈，其中问题比较多的就是关于返回``Future``的使用方法以及对异步执行的超时控制，所以这篇就来一起讲讲这两个问题的处理。

## 定义异步任务

首先，我们先使用``@Async``注解来定义一个异步任务，这个方法返回``Future``类型，具体如下：

```
@Slf4j
@Component
public class Task {

    public static Random random = new Random();

    @Async("taskExecutor")
    public Future<String> run() throws Exception {
        long sleep = random.nextInt(10000);
        log.info("开始任务，需耗时：" + sleep + "毫秒");
        Thread.sleep(sleep);
        log.info("完成任务");
        return new AsyncResult<>("test");
    }

}
```

### Tips：什么是``Future``类型？

``Future``是对于具体的``Runnable``或者``Callable``任务的执行结果进行取消、查询是否完成、获取结果的接口。必要时可以通过get方法获取执行结果，该方法会阻塞直到任务返回结果。

它的接口定义如下：

```
public interface Future<V> {
    boolean cancel(boolean mayInterruptIfRunning);
    boolean isCancelled();
    boolean isDone();
    V get() throws InterruptedException, ExecutionException;
    V get(long timeout, TimeUnit unit)
        throws InterruptedException, ExecutionException, TimeoutException;
}
```

它声明这样的五个方法：

* cancel方法用来取消任务，如果取消任务成功则返回true，如果取消任务失败则返回false。参数mayInterruptIfRunning表示是否允许取消正在执行却没有执行完毕的任务，如果设置true，则表示可以取消正在执行过程中的任务。如果任务已经完成，则无论mayInterruptIfRunning为true还是false，此方法肯定返回false，即如果取消已经完成的任务会返回false；如果任务正在执行，若mayInterruptIfRunning设置为true，则返回true，若mayInterruptIfRunning设置为false，则返回false；如果任务还没有执行，则无论mayInterruptIfRunning为true还是false，肯定返回true。
* isCancelled方法表示任务是否被取消成功，如果在任务正常完成前被取消成功，则返回 true。
* isDone方法表示任务是否已经完成，若任务完成，则返回true；
* get()方法用来获取执行结果，这个方法会产生阻塞，会一直等到任务执行完毕才返回；
* get(long timeout, TimeUnit unit)用来获取执行结果，如果在指定时间内，还没获取到结果，就直接返回null。

也就是说Future提供了三种功能： 

1. 判断任务是否完成；
2. 能够中断任务；
3. 能够获取任务执行结果。

## 测试执行与定义超时

在完成了返回``Future``的异步任务定义之后，我们来尝试实现一个单元测试来使用这个Future完成任务的执行，比如：

```
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private Task task;

    @Test
    public void test() throws Exception {
        Future<String> futureResult = task.run();
        String result = futureResult.get(5, TimeUnit.SECONDS);
        log.info(result);
    }
}
```

上面的代码中，我们在get方法中还定义了该线程执行的超时时间，通过执行这个测试我们可以观察到执行时间超过5秒的时候，这里会抛出超时异常，该执行线程就能够因执行超时而释放回线程池，不至于一直阻塞而占用资源。

[完整示例：lesson5-5](https://github.com/codeyoyo/spring-boot-learn/tree/master/springboot/lesson5-5)