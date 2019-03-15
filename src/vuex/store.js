import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

// 全局 state 对象，用于保存所有组件的公共数据
const state = sessionStorage.getItem('state') ? JSON.parse(sessionStorage.getItem('state')) : {
  // 定义一个 user 对象
  // 在组件中是通过 this.$store.state.user 来获取
  user: {
    id: '',
    username: '',
    phone: ''
  },
  isReload: false,
  // 判断订单号是否生成
  tradeNo: ''
};

// 实时监听 state 值的最新状态，注意这里的 getters 可以理解为计算属性
const getters = {
  // 在组件中是通过 this.$store.getters.getUser 来获取
  getUser(state) {
    return state.user;
  },


  getIsReload(state) {
    return state.isReload;
  },

  getTradeNo(state) {
    return state.tradeNo;
  }
};

// 定义改变 state 初始值的方法，这里是唯一可以改变 state 的地方，缺点是只能同步执行
const mutations = {
  // 在组件中是通过 this.$store.commit('updateUser', user); 方法来调用 mutations
  updateUser(state) {
    state.user = {id:'', username: '', phone: '' };
  },

  updateUserID(state, phone) {
    state.user.id = phone;
  },

  updateUserName(state, user) {
    state.user.username = user;
  },

  updateUserPhone(state, phone) {
    state.user.phone = phone;
  },

  updateIsReload(state, bool) {
    state.isReload = bool;
  },

  updateTradeNo(state, tradeNo) {
    state.tradeNo = tradeNo;
  }
};

// 定义触发 mutations 里函数的方法，可以异步执行 mutations 里的函数
const actions = {
  // 在组件中是通过 this.$store.dispatch('asyncUpdateUser', user); 来调用 actions
  asyncUpdateUser(context) {
    context.commit('updateUser');
  },

  asyncUpdateUserID(context, phone) {
    context.commit('updateUserID', phone);
  },

  asyncUpdateUserName(context, user) {
    context.commit('updateUserName', user);
  },

  asyncUpdateUserPhone(context, phone){
    context.commit('updateUserPhone', phone);
  },

  asyncUpdateIsReload(context, bool) {
    context.commit('updateIsReload', bool);
  },

  asyncUpdateTradeNo(context, tradeNo) {
    context.commit("updateTradeNo", tradeNo);
  }
};

export default new Vuex.Store({
  state,
  getters,
  mutations,
  actions
});
