import Vue from 'vue'
import App from './App.vue'
// 引入elementui
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI)
// 引入axios
import axios from 'axios';
Vue.prototype.$axios=axios;
Vue.prototype.$httpUrl='http://localhost:8888'
Vue.config.productionTip = false
// 引入vue-router
import VueRouter from 'vue-router'
import router from './router'
Vue.use(VueRouter)
// 引入vuex
import Vuex from 'vuex'
import store from './store'
Vue.use(Vuex)
// 引入datav
import dataV from '@jiaminghi/data-view'
Vue.use(dataV)

import AMap from './components/AMap.vue'
Vue.component("AMap",AMap)
import HeaderTitle from './components/Header-Title.vue'
Vue.component('HeaderTitle', HeaderTitle)
new Vue({
 render: h => h(App),
 router,
 store
}).$mount('#app')

