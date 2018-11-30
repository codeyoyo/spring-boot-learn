import commonView from '@/components';
import RESTfulAPI from "@/components/WebDev/RESTfulAPI.md";
import Thymeleaf from '@/components/WebDev/Thymeleaf.md';
import Swagger2 from '@/components/WebDev/Swagger2.md';
import Abnormity from '@/components/WebDev/Abnormity.md';
import Java8Api from '@/components/WebDev/Java8Api.md';
import XMLResponse from '@/components/WebDev/XMLResponse.md';

export default {
    path: "/Web",
    cnName: 'Web开发',
    component: commonView,
    name: "Web",
    children: [
      {
        path: 'RESTfulAPI',
        component: RESTfulAPI,
        cnName: "构建RESTful API",
        title: 'Spring Boot构建RESTful API与单元测试',
        date: "2018-11-07",
        name: "RESTfulAPI"
      },
      {
        path: "Thymeleaf",
        component: Thymeleaf,
        cnName: "Thymeleaf使用",
        title: '使用Thymeleaf模板引擎渲染web视图',
        date: '2018-11-08',
        name: "Thymeleaf"
      },
      {
        path: "Swagger2",
        component: Swagger2,
        cnName: "Swagger2构建API文档",
        title: 'Spring Boot中使用Swagger2构建强大的RESTful API文档',
        date: '2018-11-09',
        name: "Swagger2"
      },
      {
        path: "Abnormity",
        component: Abnormity,
        cnName: "统一异常处理",
        title: 'Spring Boot中Web应用的统一异常处理',
        date: '2018-11-10',
        name: "Abnormity"
      },
      {
        path: "Java8Api",
        component: Java8Api,
        title: 'Spring Boot和Feign中使用Java 8时间日期API',
        date: '2018-11-11',
        cnName: 'Java 8时间日期API',
        name: "Java8Api"
      },
      {
        path: "XMLResponse",
        component: XMLResponse,
        title: 'Spring Boot中如何扩展XML请求和响应的支持',
        date: '2018-11-12',
        cnName: 'XML请求和响应的支持',
        name: "XMLResponse"
      }
    ]
  };