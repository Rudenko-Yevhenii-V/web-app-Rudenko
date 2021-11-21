import Vue from 'vue'
import Vuex from 'vuex'

export const state = () => ({
  access: null,
    // 1234rttr
  words: null
})

export const getters = {
  getAccess(state){
    return state.access
  },
  // 1234rttr

  getWords(state){
    return state.words
  },
}

export const actions = {
  fetchAccess(context){
    this.$axios.get("/api/v1/client/access_user").then(res => {
      context.commit("setAccess", res.data)
    })
  },
  // 1234rttr
  fetchWords(context){
    this.$axios.get("/api/v1/client/words").then(res => {
      context.commit("setWords", res.data)
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
  // 1234rttr
  setWords(state, data){
    state.words = data;
  }
}
