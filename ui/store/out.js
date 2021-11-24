import Vue from 'vue'
import Vuex from 'vuex'

export const state = () => ({
  words: null,
  tests: null,
  answer: false
})

export const getters = {
  getWords(state) {
    return state.words
  },
  getTests(state) {
    return state.tests
  },
  getAnswer(state) {
    return state.tests
  },
}

export const actions = {
  fetchWords(context) {
    this.$axios.get("/api/v1/client/words").then(res => {
      context.commit("setWords", res.data)
    })
  },
  fetchTests(context, idtest) {
    this.$axios.get("/api/v1/auth/tests/" + idtest).then(res => {
      context.commit("setTests", res.data)
    })
  },
  add_words(context, data) {
    this.$axios.post("/api/v1/client/words", data).then(res => {
      context.dispatch("fetchWords")
    })
  },
  add_test_answer(context, data) {
    this.$axios.post("/api/v1/auth/tests/" + data.testId + "/users/" + data.userId + "/compete?answer=" + data.answer).then(res => {
      context.commit("setAnswer", res.data)
    })
  },
}

export const mutations = {
  setWords(state, data) {
    state.words = data;
  },
  setTests(state, data) {
    state.tests = data;
  },
  setAnswer(state, data) {
    state.tests = data;
  }
}
