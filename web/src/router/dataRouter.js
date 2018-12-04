import commonView from '@/components';
import JdbcTemplate from "@/components/Data/JdbcTemplate.md";
import Jpa from "@/components/Data/Jpa.md";
import MoreDataConfig from "@/components/Data/MoreDataConfig.md";
import Redis from "@/components/Data/Redis.md";
import MongoDB from "@/components/Data/MongoDB.md";
import MyBatis from "@/components/Data/MyBatis.md";
import MyBatisConfig from "@/components/Data/MyBatisConfig.md";
import Flyway from "@/components/Data/Flyway.md";
import LDAP from "@/components/Data/LDAP.md";
import Transaction from "@/components/Data/Transaction.md";
import MongoDBConfig from "@/components/Data/MongoDBConfig.md";

export default {
    path: "/Data",
    cnName: "数据访问",
    component: commonView,
    name: "Data",
    children: [
        {
            name: "JdbcTemplate",
            path: "JdbcTemplate",
            cnName: 'JdbcTemplate访问数据库',
            title: 'Spring Boot中使用JdbcTemplate访问数据库',
            component: JdbcTemplate,
            date: '2018-11-13'
        },
        {
            name: "Jpa",
            path: 'Jpa',
            cnName: "Spring-data-jpa使用",
            title: "Spring Boot中使用Spring-data-jpa让数据访问更简单、更优雅",
            component: Jpa,
            date: '2018-11-14'
        },
        {
            name: "MoreDataConfig",
            path: "MoreDataConfig",
            cnName: "多数据源配置",
            title: "Spring Boot多数据源配置与使用",
            component: MoreDataConfig,
            date: '2018-11-14'
        },
        {
            name: "Redis",
            path: "Redis",
            cnName: "使用NoSQL数据库（一）",
            title: "Spring Boot中使用Redis数据库",
            component: Redis,
            date: '2018-11-14'
        },
        {
            name: "MongoDB",
            path: "MongoDB",
            cnName: "使用NoSQL数据库（二）",
            title: "Spring Boot中使用MongoDB数据库",
            component: MongoDB,
            date: '2018-11-14'
        },
        {
            name: "MyBatis",
            path: "MyBatis",
            cnName: "整合MyBatis",
            title: "Spring Boot整合MyBatis",
            component: MyBatis,
            date: '2018-11-14'
        },
        {
            name: "MyBatisConfig",
            path: "MyBatisConfig",
            cnName: "MyBatis注解配置详解",
            title: "Spring Boot中使用MyBatis注解配置详解",
            component: MyBatisConfig,
            date: '2018-11-14'
        },
        {
            name: "Flyway",
            path: "Flyway",
            cnName: "Flyway管理数据库版本",
            title: "Spring Boot中使用Flyway来管理数据库版本",
            component: Flyway,
            date: '2018-11-14'
        },
        {
            name: "LDAP",
            path: "LDAP",
            cnName: "LDAP统一管理用户信息",
            title: "Spring Boot中使用LDAP来统一管理用户信息",
            component: LDAP,
            date: '2018-11-14'
        },
        {
            name: "MongoDBConfig",
            path: "MongoDBConfig",
            cnName: "增强MongoDB配置",
            title: "Spring Boot中增强对MongoDB的配置（连接池等）",
            component: MongoDBConfig,
            date: '2018-11-15'
        },
        {
            name: "Transaction",
            path: "Transaction",
            cnName: "事务管理",
            title: "Spring Boot中的事务管理",
            component: Transaction,
            date: '2018-11-16'
        }
    ]
};