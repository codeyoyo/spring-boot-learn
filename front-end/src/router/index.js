import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld';
import ConfigIntroduce from '@/components/pro_config/ConfigIntroduce';
import NewFeature1 from '@/components/pro_config/NewFeature1';
import NewFeature2 from '@/components/pro_config/NewFeature2';

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld,
      cnName: "起步"
    },
    {
      path: '/config',
      cnName: "工程配置",
      children: [
        {
          path: 'ConfigIntroduce',
          component: ConfigIntroduce,
          cnName: "配置文件详解"
        },
        {
          path: '',
          component: NewFeature1,
          cnName: "2.0 新特性（一）"
        },
        {
          path: '',
          component: NewFeature2,
          cnName: "2.0 新特性（二）"
        }
      ]
    }
  ]
})
