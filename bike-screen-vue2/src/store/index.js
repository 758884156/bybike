import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from "vuex-persistedstate";
Vue.use(Vuex)
export default new Vuex.Store({
    state: {
     CurUser:{},
     hasRoutes:false,
     Menu:[]
    },
    mutations: {
      setMenu(state,data){
        state.Menu=data
        localStorage.setItem("Menu",data)
      },
      setUser(state,data){
        state.CurUser=data
        localStorage.setItem("CurUser",data)
      },
      changeRoutesStatus(state,hasRoutes){
        state.hasRoutes=hasRoutes
      },
      resetStatus(state){
        state.Menu=[],
        state.CurUser={}
        state.hasRoutes=false
        localStorage.removeItem("CurUser")
        localStorage.removeItem("Menu")
      }

    },
    getters: {
    getMenu(state){
        return state.Menu;
    }

    },
    plugins: [createPersistedState()]
  });