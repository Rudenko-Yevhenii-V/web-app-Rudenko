<template>
  <div>
    <h1>{{ access }}</h1>
    <nav class="site-header sticky-top py-1">
      <div class="container d-flex flex-column flex-md-row justify-content-between">

        <b class="py-2 d-none d-md-inline-block" >Регистрация:</b>
        <b class="input_area py-2 d-none d-md-inline-block" ><form @submit="submitRegistration">
          <input type="text" placeholder="Name" v-model="registrationForm.name"/>
          <input type="text" placeholder="Email" v-model="registrationForm.email"/>
          <input type="password" placeholder="Password" v-model="registrationForm.password"/>
          <input type="submit" value="Зарегистрироваться"/>
        </form></b>
        <b class="py-2 d-none d-md-inline-block" >Вход:</b>
        <b class="input_area  py-2 d-none d-md-inline-block" ><form @submit="submitLogin">
          <input type="text" placeholder="Email" v-model="loginForm.email"/>
          <input  type="password" placeholder="Password" v-model="loginForm.password"/>
          <input type="submit" value="Вход"/>
          <input type="button" value="Выход" @click="submitLogout"/>
        </form></b>
        <b class="py-2 d-none d-md-inline-block" ><p>{{ accessuser }}</p></b>

      </div>
    </nav>

    <div id="out">
      <h1>All words</h1>
      {{ words }}
      <li v-for="word of words"
      >{{word.description}}</li>
      <li v-for="word of words" :key="word.name"
      >{{word.word}}</li>
    </div>
  </div>
</template>

<script>
import {mapGetters} from "vuex"
import "@/assets/main.css"
import "@/assets/bootstrap.css"


export default {
  computed: mapGetters({
    access: "user/getAccess",
    words: "user/getWords"

  }),
  fetch() {
    this.$store.dispatch("user/fetchAccess"),
    this.$store.dispatch("user/fetchWords")
  },

  data() {
    return {
      registrationForm: {
        name: null,
        email: '',
        password: ''
      },
      loginForm: {
        email: '',
        password: ''
      }
    }
  },
  methods: {
    submitLogout() {
      this.$store.dispatch("user/logout")
    },
    submitLogin(event) {
      event.preventDefault();
      this.$store.dispatch("user/login", this.loginForm)
    },
    submitRegistration(event) {
      event.preventDefault();
      this.$store.dispatch("user/registration", this.registrationForm)
    }
  }
}
</script>
