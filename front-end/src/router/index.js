import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld';

import configRouter from "./configRouter";
import dataRouter from "./dataRouter";
import webRouter from "./webRouter";

Vue.use(Router);

const router = new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld,
      cnName: "起步"
    },
    configRouter,
    webRouter,
    dataRouter
  ]
});

export default router;
