import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld';

import configRouter from "./configRouter";
import dataRouter from "./dataRouter";
import webRouter from "./webRouter";
import asyncRouter from "./asyncRouter";
import logRouter from "./logRouter";

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
    dataRouter,
    asyncRouter,
    logRouter
  ]
});

export default router;
