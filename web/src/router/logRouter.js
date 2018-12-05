import commonView from '@/components';
import defaultLog from "@/components/Log/defaultLog.md";
import log4j from "@/components/Log/log4j.md";
import logLevel from "@/components/Log/logLevel.md";
import requestLog from "@/components/Log/requestLog.md";

export default{
    path:'/log',
    cnName:'日志管理',
    component:commonView,
    name:"log",
    children:[
        {
            path:'defaultLog',
            component:defaultLog,
            cnName:'默认日志配置',
            title:'Spring Boot日志管理',
            date:'2018-11-21',
            name:'defaultLog'
        },
        {
            path:"log4j",
            component:log4j,
            cnName:"log4j日志使用",
            title:'Spring boot中使用log4j记录日志',
            date:'2018-11-22',
            name:'log4j'
        },
        {
            path:"logLevel",
            component:logLevel,
            cnName:"日志级别控制",
            title:"Spring Boot中对log4j进行多环境不同日志级别的控制",
            date:'2018-11-23',
            name:"logLevel"
        },
        {
            path:"requestLog",
            component:requestLog,
            cnName:"统一处理请求日志",
            title:"Spring Boot中使用AOP统一处理Web请求日志",
            date:'2018-11-24',
            name:"requestLog"
        }
    ]
}