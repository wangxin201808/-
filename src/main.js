// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import VueRouter from 'vue-router'
import Vuex from 'vuex'
import store from './vuex/store'

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import iView from 'iview';
import 'iview/dist/styles/iview.css';

import routes from './router/index';
import axios from 'axios';
Vue.prototype.$ajax = axios;


Vue.use(Vuex);
Vue.use(ElementUI);
Vue.use(iView);
Vue.use(VueRouter);


const router = new VueRouter({
  routes
})




/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
