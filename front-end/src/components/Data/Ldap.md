> 很多时候，我们在构建系统的时候都会自己创建用户管理体系，这对于开发人员来说并不是什么难事，但是当我们需要维护多个不同系统并且相同用户跨系统使用的情况下，如果每个系统维护自己的用户信息，那么此时用户信息的同步就会变的比较麻烦，对于用户自身来说也会非常困扰，很容易出现不同系统密码不一致啊等情况出现。如果此时我们引入LDAP来集中存储用户的基本信息并提供统一的读写接口和校验机制，那么这样的问题就比较容易解决了。下面就来说说当我们使用Spring Boot开发的时候，如何来访问LDAP服务端。

## LDAP简介

LDAP（轻量级目录访问协议，Lightweight Directory Access Protocol)是实现提供被称为目录服务的信息服务。目录服务是一种特殊的数据库系统，其专门针对读取，浏览和搜索操作进行了特定的优化。目录一般用来包含描述性的，基于属性的信息并支持精细复杂的过滤能力。目录一般不支持通用数据库针对大量更新操作操作需要的复杂的事务管理或回卷策略。而目录服务的更新则一般都非常简单。这种目录可以存储包括个人信息、web链结、jpeg图像等各种信息。为了访问存储在目录中的信息，就需要使用运行在TCP/IP 之上的访问协议—LDAP。

LDAP目录中的信息是是按照树型结构组织，具体信息存储在条目(entry)的数据结构中。条目相当于关系数据库中表的记录；条目是具有区别名DN （Distinguished Name）的属性（Attribute），DN是用来引用条目的，DN相当于关系数据库表中的关键字（Primary Key）。属性由类型（Type）和一个或多个值（Values）组成，相当于关系数据库中的字段（Field）由字段名和数据类型组成，只是为了方便检索的需要，LDAP中的Type可以有多个Value，而不是关系数据库中为降低数据的冗余性要求实现的各个域必须是不相关的。LDAP中条目的组织一般按照地理位置和组织关系进行组织，非常的直观。LDAP把数据存放在文件中，为提高效率可以使用基于索引的文件数据库，而不是关系数据库。类型的一个例子就是mail，其值将是一个电子邮件地址。

LDAP的信息是以树型结构存储的，在树根一般定义国家(c=CN)或域名(dc=com)，在其下则往往定义一个或多个组织 (organization)(o=Acme)或组织单元(organizational units) (ou=People)。一个组织单元可能包含诸如所有雇员、大楼内的所有打印机等信息。此外，LDAP支持对条目能够和必须支持哪些属性进行控制，这是有一个特殊的称为对象类别(objectClass)的属性来实现的。该属性的值决定了该条目必须遵循的一些规则，其规定了该条目能够及至少应该包含哪些属性。例如：inetorgPerson对象类需要支持sn(surname)和cn(common name)属性，但也可以包含可选的如邮件，电话号码等属性。

### LDAP简称对应

* o：organization（组织-公司）
* ou：organization unit（组织单元-部门）
* c：countryName（国家）
* dc：domainComponent（域名）
* sn：surname（姓氏）
* cn：common name（常用名称）

*以上内容参考自*：[LDAP快速入门](https://www.cnblogs.com/obpm/archive/2010/08/28/1811065.html)

## 入门示例

在了解了LDAP的基础概念之后，我们通过一个简单例子进一步理解！

* 创建一个基础的Spring Boot项目

* 在``pom.xml``中引入两个重要依赖

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-ldap</artifactId>
</dependency>

<dependency>
    <groupId>com.unboundid</groupId>
    <artifactId>unboundid-ldapsdk</artifactId>
    <scope>test</scope>
</dependency>
```

其中，``spring-boot-starter-data-ldap``是Spring Boot封装的对LDAP自动化配置的实现，它是基于spring-data-ldap来对LDAP服务端进行具体操作的。

而``unboundid-ldapsdk``主要是为了在这里使用嵌入式的LDAP服务端来进行测试操作，所以scope设置为了test，实际应用中，我们通常会连接真实的、独立部署的LDAP服务器，所以不需要此项依赖。

* 在``src/test/resources``目录下创建``ldap-server.ldif``文件，用来存储LDAP服务端的基础数据，以备后面的程序访问之用。

```
dn: dc=didispace,dc=com
objectClass: top
objectClass: domain

dn: ou=people,dc=didispace,dc=com
objectclass: top
objectclass: organizationalUnit
ou: people

dn: uid=ben,ou=people,dc=didispace,dc=com
objectclass: top
objectclass: person
objectclass: organizationalPerson
objectclass: inetOrgPerson
cn: didi
sn: zhaiyongchao
uid: didi
userPassword: {SHA}nFCebWjxfaLbHHG1Qk5UU4trbvQ=
```

这里创建了一个基础用户，真实姓名为``zhaiyongchao``,常用名``didi``，在后面的程序中，我们会来读取这些信息。更多内容解释大家可以深入学习LDAP来理解，这里不做过多的讲解。

* 在``application.properties``中添加嵌入式LDAP的配置

```
spring.ldap.embedded.ldif=ldap-server.ldif
spring.ldap.embedded.base-dn=dc=didispace,dc=com
```

* 使用spring-data-ldap的基础用法，定义LDAP中属性与我们Java中定义实体的关系映射以及对应的Repository

```
@Data
@Entry(base = "ou=people,dc=didispace,dc=com", objectClasses = "inetOrgPerson")
public class Person {

    @Id
    private Name id;
    @DnAttribute(value = "uid", index = 3)
    private String uid;
    @Attribute(name = "cn")
    private String commonName;
    @Attribute(name = "sn")
    private String suerName;
    private String userPassword;

}

public interface PersonRepository extends CrudRepository<Person, Name> {

}
```

通过上面的定义之后，已经将Person对象与LDAP存储内容实现了映射，我们只需要使用``PersonRepository``就可以轻松的对LDAP内容实现读写。

* 创建单元测试用例读取所有用户信息：

```
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Autowired
	private PersonRepository personRepository;

	@Test
	public void findAll() throws Exception {
		personRepository.findAll().forEach(p -> {
			System.out.println(p);
		});
	}
}
```

启动该测试用例之后，我们可以看到控制台中输出了刚才维护在``ldap-server.ldif``中的用户信息：

```
2018-01-27 14:25:06.283  WARN 73630 --- [           main] o.s.ldap.odm.core.impl.ObjectMetaData    : The Entry class Person should be declared final
Person(id=uid=ben,ou=people,dc=didispace,dc=com, uid=ben, commonName=didi, suerName=zhaiyongchao, userPassword=123,83,72,65,125,110,70,67,101,98,87,106,120,102,97,76,98,72,72,71,49,81,107,53,85,85,52,116,114,98,118,81,61)
```

### 添加用户

通过上面的入门示例，如果您能够独立完成，那么在Spring Boot中操作LDAP的基础目标已经完成了。

如果您足够了解Spring Data，其实不难想到，这个在其下的子项目必然也遵守Repsitory的抽象。所以，我们可以使用上面定义的``PersonRepository``来轻松实现操作，比如下面的代码就可以方便的往LDAP中添加用户：

```
Person person = new Person();
person.setUid("uid:1");
person.setSuerName("AAA");
person.setCommonName("aaa");
person.setUserPassword("123456");
personRepository.save(person);
```

如果还想实现更多操作，您可以参考spring-data-ldap的文档来进行使用。

### 连接LDAP服务端

在本文的例子中都采用了嵌入式的LDAP服务器，事实上这种方式也仅限于我们本地测试开发使用，真实环境下LDAP服务端必然是独立部署的。

在Spring Boot的封装下，我们只需要配置下面这些参数就能将上面的例子连接到远端的LDAP而不是嵌入式的LDAP。

```
spring.ldap.urls=ldap://localhost:1235
spring.ldap.base=dc=didispace,dc=com
spring.ldap.username=didispace
spring.ldap.password=123456
```

## 本文代码

可以通过下面两个仓库中查阅``chapter3-2-10``目录：

[完整示例](完整示例)
