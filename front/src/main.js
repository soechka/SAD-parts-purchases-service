import Vue from 'vue'
import './plugins/bootstrap-vue'
import './plugins/format'
import App from './App.vue'

Vue.config.productionTip = false
var VueCookie = require('vue-cookie')
Vue.use(VueCookie)
new Vue({
  render: h => h(App),
}).$mount('#app')
