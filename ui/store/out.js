import Vue from 'vue'
import Vuex from 'vuex'

export const state = () => ({
  words: null
})

export const getters = {
  getWords(state){
    return state.words
  },
}

export const actions = {
  fetchWords(context){
    this.$axios.get("/api/v1/client/words").then(res => {
      context.commit("setWords", res.data)
    })
  },
  add_words(context, data){
    this.$axios.post("/api/v1/client/words", data).then(res => {
      context.dispatch("fetchWords")
    })
  },
}

export const mutations = {
  setWords(state, data){
    state.words = data;
  }
}
