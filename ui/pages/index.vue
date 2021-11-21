<template>
  <div>
    <nav class="input_area site-header sticky-top py-1">
      <div class="container d-flex flex-column flex-md-row justify-content-between">
        <b class="py-sm-1 d-none d-md-inline-block" ><h1 class=" asses">{{ access }}</h1></b>
        <b class="py-2 d-none d-md-inline-block" >Регистрация:</b>
        <b class="input_area py-2 d-none d-md-inline-block" >
          <form @submit="submitRegistration">
          <div><input type="text" placeholder="Name" v-model="registrationForm.name"/></div>
            <div><input type="text" placeholder="Email" v-model="registrationForm.email"/></div>
            <div> <input type="password" placeholder="Password" v-model="registrationForm.password"/></div>
            <div> <input  class="btn btn-info" type="submit" value="Зарегистрироваться"/></div>
        </form></b>
        <b class="py-2 d-none d-md-inline-block" >Ввести данные:</b>
        <b class="input_area py-2 d-none d-md-inline-block" >
          <form @submit="submitUpdate">
          <div><input type="text" placeholder="Name" v-model="updateForm.name"/></div>
            <div><input type="text" placeholder="middleName" v-model="updateForm.middleName"/></div>
            <div> <input type="text" placeholder="lastName" v-model="updateForm.lastName"/></div>
            <div> <input type="text" placeholder="birthday" v-model="updateForm.birthday"/></div>
            <div> <input  class="btn btn-info" type="submit" value="update"/></div>
        </form></b>
        <b class="py-2 d-none d-md-inline-block" >Вход:</b>
        <b class="input_area  py-2 d-none d-md-inline-block" >
          <form @submit="submitLogin">
            <div><input type="text" placeholder="Email" v-model="loginForm.email"/></div>
              <div><input  type="password" placeholder="Password" v-model="loginForm.password"/></div>
                <div><input class="btn btn-info" type="submit" value="Вход"/></div>
                  <div><input class="btn btn-info" type="button" value="Выход" @click="submitLogout"/></div>
        </form></b>
      </div>
    </nav>
&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
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
        name: '',
        email: '',
        password: ''
      },
      updateForm: {
        name: '',
        middleName: '',
        lastName: '',
        birthday: ''
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
    },
    submitUpdate(event) {
      event.preventDefault();
      this.$store.dispatch("user/update", this.updateForm)
    }
  }
}
</script>
