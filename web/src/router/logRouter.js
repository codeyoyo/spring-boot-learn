import commonView from '@/components';
import defaultLog from "@/components/Log/defaultLog.md";
import log4j from "@/components/Log/log4j.md";

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
        }
    ]
}