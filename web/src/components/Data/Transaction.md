## 什么是事务？

我们在开发企业应用时，对于业务人员的一个操作实际是对数据读写的多步操作的结合。由于数据操作在顺序执行的过程中，任何一步操作都有可能发生异常，异常会导致后续操作无法完成，此时由于业务逻辑并未正确的完成，之前成功操作数据的并不可靠，需要在这种情况下进行回退。

事务的作用就是为了保证用户的每一个操作都是可靠的，事务中的每一步操作都必须成功执行，只要有发生异常就回退到事务开始未进行操作的状态。

事务管理是Spring框架中最为常用的功能之一，我们在使用Spring Boot开发应用时，大部分情况下也都需要使用事务。

## 快速入门

在Spring Boot中，当我们使用了spring-boot-starter-jdbc或spring-boot-starter-data-jpa依赖的时候，框架会自动默认分别注入DataSourceTransactionManager或JpaTransactionManager。所以我们不需要任何额外配置就可以用@Transactional注解进行事务的使用。

我们以之前实现的《用spring-data-jpa访问数据库》的示例[Chapter3-2-2](Chapter3-2-2)作为基础工程进行事务的使用常识。

在该样例工程中（若对该数据访问方式不了解，可先阅读该[文章](/Data/Jpa)），我们引入了spring-data-jpa，并创建了User实体以及对User的数据访问对象UserRepository，在ApplicationTest类中实现了使用UserRepository进行数据读写的单元测试用例，如下：

```
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class ApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void test() throws Exception {

		// 创建10条记录
		userRepository.save(new User("AAA", 10));
		userRepository.save(new User("BBB", 20));
		userRepository.save(new User("CCC", 30));
		userRepository.save(new User("DDD", 40));
		userRepository.save(new User("EEE", 50));
		userRepository.save(new User("FFF", 60));
		userRepository.save(new User("GGG", 70));
		userRepository.save(new User("HHH", 80));
		userRepository.save(new User("III", 90));
		userRepository.save(new User("JJJ", 100));

		// 省略后续的一些验证操作
	}
}
```

可以看到，在这个单元测试用例中，使用UserRepository对象连续创建了10个User实体到数据库中，下面我们人为的来制造一些异常，看看会发生什么情况。

通过定义User的name属性长度为5，这样通过创建时User实体的name属性超长就可以触发异常产生。

```
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 5)
    private String name;

    @Column(nullable = false)
    private Integer age;

    // 省略构造函数、getter和setter

}
```

修改测试用例中创建记录的语句，将一条记录的name长度超过5，如下：name为HHHHHHHHH的User对象将会抛出异常。

```
// 创建10条记录
userRepository.save(new User("AAA", 10));
userRepository.save(new User("BBB", 20));
userRepository.save(new User("CCC", 30));
userRepository.save(new User("DDD", 40));
userRepository.save(new User("EEE", 50));
userRepository.save(new User("FFF", 60));
userRepository.save(new User("GGG", 70));
userRepository.save(new User("HHHHHHHHHH", 80));
userRepository.save(new User("III", 90));
userRepository.save(new User("JJJ", 100));
```

执行测试用例，可以看到控制台中抛出了如下异常，name字段超长：

```
2016-05-27 10:30:35.948  WARN 2660 --- [           main] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Error: 1406, SQLState: 22001
2016-05-27 10:30:35.948 ERROR 2660 --- [           main] o.h.engine.jdbc.spi.SqlExceptionHelper   : Data truncation: Data too long for column 'name' at row 1
2016-05-27 10:30:35.951  WARN 2660 --- [           main] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Warning Code: 1406, SQLState: HY000
2016-05-27 10:30:35.951  WARN 2660 --- [           main] o.h.engine.jdbc.spi.SqlExceptionHelper   : Data too long for column 'name' at row 1

org.springframework.dao.DataIntegrityViolationException: could not execute statement; SQL [n/a]; nested exception is org.hibernate.exception.DataException: could not execute statement
```

此时查数据库中，创建了name从AAA到GGG的记录，没有HHHHHHHHHH、III、JJJ的记录。而若这是一个希望保证完整性操作的情况下，AAA到GGG的记录希望能在发生异常的时候被回退，这时候就可以使用事务让它实现回退，做法非常简单，我们只需要在test函数上添加``@Transactional``注解即可。

```
@Test
@Transactional
public void test() throws Exception {

    // 省略测试内容

}
```

再来执行该测试用例，可以看到控制台中输出了回滚日志（Rolled back transaction for test context）

```
2016-05-27 10:35:32.210  WARN 5672 --- [           main] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Error: 1406, SQLState: 22001
2016-05-27 10:35:32.210 ERROR 5672 --- [           main] o.h.engine.jdbc.spi.SqlExceptionHelper   : Data truncation: Data too long for column 'name' at row 1
2016-05-27 10:35:32.213  WARN 5672 --- [           main] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Warning Code: 1406, SQLState: HY000
2016-05-27 10:35:32.213  WARN 5672 --- [           main] o.h.engine.jdbc.spi.SqlExceptionHelper   : Data too long for column 'name' at row 1
2016-05-27 10:35:32.221  INFO 5672 --- [           main] o.s.t.c.transaction.TransactionContext   : Rolled back transaction for test context [DefaultTestContext@1d7a715 testClass = ApplicationTests, testInstance = com.didispace.ApplicationTests@95a785, testMethod = test@ApplicationTests, testException = org.springframework.dao.DataIntegrityViolationException: could not execute statement; SQL [n/a]; nested exception is org.hibernate.exception.DataException: could not execute statement, mergedContextConfiguration = [MergedContextConfiguration@11f39f9 testClass = ApplicationTests, locations = '{}', classes = '{class com.didispace.Application}', contextInitializerClasses = '[]', activeProfiles = '{}', propertySourceLocations = '{}', propertySourceProperties = '{}', contextLoader = 'org.springframework.boot.test.SpringApplicationContextLoader', parent = [null]]].

org.springframework.dao.DataIntegrityViolationException: could not execute statement; SQL [n/a]; nested exception is org.hibernate.exception.DataException: could not execute statement
```

再看数据库中，User表就没有AAA到GGG的用户数据了，成功实现了自动回滚。

这里主要通过单元测试演示了如何使用``@Transactional``注解来声明一个函数需要被事务管理，通常我们单元测试为了保证每个测试之间的数据独立，会使用``@Rollback``注解让每个单元测试都能在结束时回滚。而真正在开发业务逻辑时，我们通常在service层接口中使用``@Transactional``来对各个业务逻辑进行事务管理的配置，例如：

```
public interface UserService {
    
    @Transactional
    User login(String name, String password);
    
}
```

### 事务详解

上面的例子中我们使用了默认的事务配置，可以满足一些基本的事务需求，但是当我们项目较大较复杂时（比如，有多个数据源等），这时候需要在声明事务时，指定不同的事务管理器。对于不同数据源的事务管理配置可以见[《Spring Boot多数据源配置与使用》](/Data/MoreDataConfig)中的设置。在声明事务时，只需要通过value属性指定配置的事务管理器名即可，例如：``@Transactional(value="transactionManagerPrimary")``。

除了指定不同的事务管理器之后，还能对事务进行隔离级别和传播行为的控制，下面分别详细解释：

__隔离级别__

隔离级别是指若干个并发的事务之间的隔离程度，与我们开发时候主要相关的场景包括：脏读取、重复读、幻读。

我们可以看``org.springframework.transaction.annotation.Isolation``枚举类中定义了五个表示隔离级别的值：

```
public enum Isolation {
    DEFAULT(-1),
    READ_UNCOMMITTED(1),
    READ_COMMITTED(2),
    REPEATABLE_READ(4),
    SERIALIZABLE(8);
}
```

* ``DEFAULT``：这是默认值，表示使用底层数据库的默认隔离级别。对大部分数据库而言，通常这值就是：READ_COMMITTED。
* ``READ_UNCOMMITTED``：该隔离级别表示一个事务可以读取另一个事务修改但还没有提交的数据。该级别不能防止脏读和不可重复读，因此很少使用该隔离级别。
* ``READ_COMMITTED``：该隔离级别表示一个事务只能读取另一个事务已经提交的数据。该级别可以防止脏读，这也是大多数情况下的推荐值。
* ``REPEATABLE_READ``：该隔离级别表示一个事务在整个过程中可以多次重复执行某个查询，并且每次返回的记录都相同。即使在多次查询之间有新增的数据满足该查询，这些新增的记录也会被忽略。该级别可以防止脏读和不可重复读。
* ``SERIALIZABLE``：所有的事务依次逐个执行，这样事务之间就完全不可能产生干扰，也就是说，该级别可以防止脏读、不可重复读以及幻读。但是这将严重影响程序的性能。通常情况下也不会用到该级别。

指定方法：通过使用``isolation``属性设置，例如：

```
@Transactional(isolation = Isolation.DEFAULT)
```

### 传播行为

所谓事务的传播行为是指，如果在开始当前事务之前，一个事务上下文已经存在，此时有若干选项可以指定一个事务性方法的执行行为。

我们可以看``org.springframework.transaction.annotation.Propagation``枚举类中定义了6个表示传播行为的枚举值：

```
public enum Propagation {
    REQUIRED(0),
    SUPPORTS(1),
    MANDATORY(2),
    REQUIRES_NEW(3),
    NOT_SUPPORTED(4),
    NEVER(5),
    NESTED(6);
}
```

* ``REQUIRED``：如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。
* ``SUPPORTS``：如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行。
* ``MANDATORY``：如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。
* ``REQUIRES_NEW``：创建一个新的事务，如果当前存在事务，则把当前事务挂起。
* ``NOT_SUPPORTED``：以非事务方式运行，如果当前存在事务，则把当前事务挂起。
* ``NEVER``：以非事务方式运行，如果当前存在事务，则抛出异常。
* ``NESTED``：如果当前存在事务，则创建一个事务作为当前事务的嵌套事务来运行；如果当前没有事务，则该取值等价于``REQUIRED``。

指定方法：通过使用``propagation``属性设置，例如：

```
@Transactional(propagation = Propagation.REQUIRED)
```

完整示例[Chapter3-3-1](Chapter3-3-1)