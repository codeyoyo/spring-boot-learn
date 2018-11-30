import commonView from '@/components';
import scheduled from "@/components/Async/scheduled.md";
import asyncCall from "@/components/Async/asyncCall.md";
import taskExecutor from "@/components/Async/taskExecutor.md";
import taskClose from "@/components/Async/taskClose.md";

export default{
    path:'/async',
    cnName:'定时任务与异步',
    component:commonView,
    name:"async",
    children:[
        {
            path:'scheduled',
            component:scheduled,
            cnName:'创建定时任务',
            title:'Spring Boot中使用@Scheduled创建定时任务',
            date:'2018-11-16',
            name:'scheduled'
        },
        {
            path:"asyncCall",
            component:asyncCall,
            cnName:"异步调用实现",
            title:"Spring Boot中使用@Async实现异步调用",
            date:"2018-11-17",
            name:"asyncCall"
        },
        {
            path:"taskExecutor",
            component:taskExecutor,
            cnName:"自定义线程池",
            title:"Spring Boot使用@Async实现异步调用：自定义线程池",
            date:"2018-11-18",
            name:"taskExecutor"
        },
        {
            path:"taskClose",
            component:taskClose,
            cnName:"资源优雅关闭",
            title:"Spring Boot使用@Async实现异步调用：ThreadPoolTaskScheduler线程池的优雅关闭",
            date:"2018-11-19",
            name:"taskClose"
        }
    ]
}