// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import App from './App';
import router from './router';
import iView from 'iview';
import 'iview/dist/styles/iview.css';
import '@/styles/animate.css';
import '@/styles/common.less';
import 'highlight.js/styles/github.css';
import hljs from "highlight.js";

Vue.config.productionTip = false;
Vue.use(iView);

Vue.directive('highlight', (el) => {
  let blocks = el.querySelectorAll("pre code");
  blocks.forEach(block => {
    hljs.highlightBlock(block);
  });
});
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
