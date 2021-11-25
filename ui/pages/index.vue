<template>
  <div>
    <nav class="input_area site-header sticky-top py-1">
      <div class="container d-flex flex-column flex-md-row justify-content-between">
        <b class="py-sm-1 d-none d-md-inline-block"><h1 class=" asses">{{ access }}</h1></b>
        <b class="py-2 d-none d-md-inline-block">Регистрация:</b>
        <b class="input_area py-2 d-none d-md-inline-block">
          <form @submit="submitRegistration">
            <div><input type="text" placeholder="Name" v-model="registrationForm.name"/></div>
            <div><input type="text" placeholder="Email" v-model="registrationForm.email"/></div>
            <div><input type="password" placeholder="Password" v-model="registrationForm.password"/></div>
            <div><input class="btn btn-info" type="submit" value="Зарегистрироваться"/></div>
          </form>
        </b>
        <b class="py-2 d-none d-md-inline-block">Ввести данные:</b>
        <b class="input_area py-2 d-none d-md-inline-block">
          <form @submit="submitUpdate">
            <div><input type="text" placeholder="Name" v-model="updateForm.name"/></div>
            <div><input type="text" placeholder="middleName" v-model="updateForm.middleName"/></div>
            <div><input type="text" placeholder="lastName" v-model="updateForm.lastName"/></div>
            <div><input type="text" placeholder="birthday" v-model="updateForm.birthday"/></div>
            <div><input class="btn btn-info" type="submit" value="update"/></div>
          </form>
        </b>
        <b class="py-2 d-none d-md-inline-block">Вход:</b>
        <b class="input_area  py-2 d-none d-md-inline-block">
          <form @submit="submitLogin">
            <div><input type="text" placeholder="Email" v-model="loginForm.email"/></div>
            <div><input type="password" placeholder="Password" v-model="loginForm.password"/></div>
            <div><input class="btn btn-info" type="submit" value="Вход"/></div>
            <div><input class="btn btn-info" type="button" value="Выход" @click="submitLogout"/></div>
          </form>
        </b>


      </div>
    </nav>
<div class="test_class">
    <h1>Tests</h1>
  <form @submit="submitAdd_tests">
    <div><input type="text" placeholder="testName" v-model="add_testForm.testName"/></div>
    <div><input type="text" placeholder="questionOrder" v-model="add_testForm.questionOrder"/></div>
    <div><input type="text" placeholder="questionText" v-model="add_testForm.questionText"/></div>
    <div><input type="text" placeholder="answerOrder" v-model="add_testForm.answerOrder"/></div>
    <div><input type="text" placeholder="answerText" v-model="add_testForm.answerText"/></div>
    <div><input type="text" placeholder="falseAnswerText1" v-model="add_testForm.falseAnswerText1"/></div>
    <div><input type="text" placeholder="falseAnswerText2" v-model="add_testForm.falseAnswerText2"/></div>
    <div><input type="text" placeholder="falseAnswerText3" v-model="add_testForm.falseAnswerText3"/></div>
    <div><input class="btn btn-info" type="submit" value="ADD TEST"/></div>
  </form>
  test id = {{ idAddingTest.id }}<br/>
  test name = {{ idAddingTest.name }}<br/>
question text => {{ idAddingTest.questions.text }}


  <li v-for="answer of idAddingTest.questions.answers"
  >answer => {{ answer.id}} -- {{ answer.name}}
  </li>




  <hr>
  <h5>Seach  tests</h5>
    <b class="input_area  py-2 d-none d-md-inline-block">
      <form @submit="submitIdTest()">
        <div><input type="text" placeholder="id" v-model="idForm" v-on:keyup.enter="setIdTest" /></div>
        <div><input class="btn btn-info" type="button" value="Id Test" @click="submitIdTest()"/></div>
      </form>
    </b>
  <hr>
  {{ answer }}
  <form @submit="submitAdd_anwser">
    <div><input type="text" placeholder="userId" v-model="setAnswerForm.userId"/></div>
    <div><input type="text" placeholder="testId" v-model="setAnswerForm.testId"/></div>
    <div><input type="text" placeholder="answerId" v-model="setAnswerForm.answer"/></div>
    <div><input class="btn btn-info" type="submit" value="OK"/></div>
  </form>
</div>
    <h1>USERS</h1>
    <ul>
      <li v-for="all_user of all_users"
      >
        ID: {{ all_user.id }} ===>  Name: {{ all_user.name }}   ===>    Email: {{ all_user.email }}
      </li>
    </ul>
    <h1>For ADMIN: </h1>
    <div class="input_area site-header sticky-top py-1">
      <div class="container">
        <b class="input_area py-2 d-none d-md-inline-block">
          <form @submit="submitUpdateRole">
            <div><input type="text" placeholder="id_user" v-model="updateRole.id"/></div>
            <div><input type="text" placeholder="role" v-model="updateRole.role"/></div>
            <div><input class="btn btn-info" type="submit" value="set role"/></div>
          </form>
        </b>
      </div>
      <div class="container">
        <b class="input_area py-2 d-none d-md-inline-block">
          <form @submit="submitAdd_words">
            <div><input type="text" placeholder="word" v-model="add_wordsForm.word"/></div>
            <div><input type="text" placeholder="description" v-model="add_wordsForm.description"/></div>
            <div><input class="btn btn-info" type="submit" value="add word"/></div>
          </form>
        </b>
      </div>
      <div class="container">
        <div id="out">
          <h1>words</h1>
          <li v-for="word of words"
          >word: {{ word.word }} => description: {{ word.description }}
          </li>
        </div>
      </div>

    </div>


  </div>
</template>

<script>
import Vue from 'vue'
import {mapGetters} from "vuex"
import "@/assets/bootstrap.css"
import "@/assets/main.css"

export default {
  computed: mapGetters({
    access: "user/getAccess",
    words: "out/getWords",
    all_users: "user/getAllUsers",
    tests: "out/getTests",
    answer: "out/getAnswer",
    idAddingTest: "out/getIdAddingTest"
  }),
  fetch() {
    this.$store.dispatch("user/fetchAccess")
      this.$store.dispatch("out/fetchWords")
      this.$store.dispatch("user/fetchAllUsers")
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
      },

      idForm: '',
      updateRole: {
        id: '',
        role: ''
      },
      setAnswerForm: {
        testId: '',
        userId: '',
        answer: ''
      },
      add_wordsForm: {
        word: '',
        description: ''
      },
      add_testForm: {
        testName: '',
        questionOrder: '',
        questionText: '',
         answerOrder: '',
         answerText: '',
        falseAnswerText1: '',
         falseAnswerText2: '',
         falseAnswerText3: ''
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
    },
    submitUpdateRole(event) {
      event.preventDefault();
      this.$store.dispatch("user/update_role", this.updateRole)
    },
    submitAdd_words(event) {
      event.preventDefault();
      this.$store.dispatch("out/add_words", this.add_wordsForm)
    },
    submitAdd_tests(event) {
      event.preventDefault();
      this.$store.dispatch("out/add_tests", this.add_testForm)
    },
    submitAdd_anwser(event) {
      event.preventDefault();
      this.$store.dispatch("out/add_test_answer", this.setAnswerForm)
    },
    submitIdTest() {
      this.$store.dispatch("out/fetchTests", this.idForm)
    },
    setIdTest() {
      this.submitIdTest(this.idForm)
    }
  }
}
</script>
