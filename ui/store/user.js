import Vue from 'vue'
import Vuex from 'vuex'

export const state = () => ({
  access: null,
  words: null,
  all_users: null
})

export const getters = {
  getAccess(state){
    return state.access
  },

  getWords(state){
    return state.words
  },
  getAllUsers(state){
    return state.all_users
  }
}

export const actions = {
  fetchAccess(context){
    this.$axios.get("/api/v1/client/access_user").then(res => {
      context.commit("setAccess", res.data)
    })
  },
  fetchWords(context){
    this.$axios.get("/api/v1/client/words").then(res => {
      context.commit("setWords", res.data)
    })
  },
  fetchAllUsers(context){
    this.$axios.get("/api/v1/auth/admin/users/all").then(res => {
      context.commit("setAllUsers", res.data)
    })
  },
  login(context, data){
    this.$axios.post("/api/v1/auth/login", data).then(res => {
      context.dispatch("fetchAccess")
    })
  },
  registration(context, data){
    this.$axios.post("/api/v1/auth/registration", data).then(res => {
      context.dispatch("fetchAccess")
    })
  },
  update(context, data){
    this.$axios.put("/api/v1/auth/users/update", data).then(res => {
      context.dispatch("fetchAccess")
    })
  },
  update_role(context, data){
    this.$axios.put("/api/v1/auth/admin/users/update/role", data).then(res => {
      context.dispatch("fetchAccess")
    })
  },
  logout(context){
    this.$axios.get("/api/v1/auth/logout").then(res => {
      context.dispatch("fetchAccess")
    })
  }
}

export const mutations = {
  setAccess(state, data){
    state.access = data
  },
  setAllUsers(state, data){
    state.all_users = data
  },
  setWords(state, data){
    state.words = data;
  }
}
