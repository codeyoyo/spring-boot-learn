import commonView from '@/components';
import configlntroduce from "@/components/Configlntroduce/configlntroduce.md";
import newfeature1 from "@/components/Configlntroduce/newfeature1.md";
import newfeature2 from "@/components/Configlntroduce/newfeature2.md";

export default {
    path: '/config',
    cnName: "工程配置",
    component: commonView,
    name: "config",
    children: [
      {
        path: 'ConfigIntroduce',
        component: configlntroduce,
        cnName: "配置文件详解",
        title: 'Spring Boot属性配置文件详解',
        date: "2018-11-04",
        name: "ConfigIntroduce"
      },
      {
        path: 'NewFeature1',
        component: newfeature1,
        cnName: "2.0 新特性（一）",
        title: "Spring Boot 2.0 新特性（一）：配置绑定 2.0 全解析",
        date: "2018-11-05",
        name: "NewFeature1"
      },
      {
        path: 'NewFeature2',
        component: newfeature2,
        cnName: "2.0 新特性（二）",
        title: "Spring Boot 2.0 新特性（二）：新增事件ApplicationStartedEvent",
        date: "2018-11-06",
        name: "NewFeature2"
      }
    ]
  };