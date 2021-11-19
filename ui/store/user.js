// new Vue({
//   el: '#out',
//   data: {
//     acces: {
//       name: '',
//       email: '',
//       enable: true
//     }
//   }
// })
export const state = () => ({
  access: null,
    // 1234rttr
  accessuser: null
})

export const getters = {
  getAccess(state){
    return state.access
  },
  // 1234rttr

  getAccessuser(state){
    return state.accessuser
  },
}

export const actions = {
  fetchAccess(context){
    this.$axios.get("/api/v1/auth/current").then(res => {
      context.commit("setAccess", res.data)
    })
  },
  // 1234rttr
  fetchAccessuser(context){
    this.$axios.get("/api/v1/client/accessUser").then(res => {
      context.commit("setAccessuser", res.data)
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
  setAccessuser(state, data){
    state.accessuser = data;
  }
}
