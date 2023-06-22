<template>
  <div>
    <CustomModalComponent
      ref="custom-modal"
      :okHandler="submit"
      :title="title"
    >
      <template v-slot:body>
        <form ref="form">
          <b-form-group
            label="Логин"
            label-for="username-input"
            invalid-feedback="Неверный формат логина"
            :state="loginState"
          >
            <b-form-input
              id="username-input"
              v-model="data.login"
              :state="loginState"
              :update="badUsernameMessage = null"
              placeholder="Введите логин"
              debounce="500"
              required
            ></b-form-input>
          </b-form-group>

          <b-form-group
            label="Пароль"
            label-for="password-input"
            invalid-feedback="Пароль должен содержать больше 8"
            :state="passwordState"
          >
            <b-form-input
              id="password-input"
              v-model="data.password"
              :update="badPasswordMessage = null"
              :state="passwordState"
              placeholder="Введите пароль"
              debounce="500"
              type="password"
              required
            ></b-form-input>
          </b-form-group>

          <b-form-group
            label="Роль"
            label-for="role-radio"
          >
            <b-form-radio-group
              id="role-radio"
              v-model="selectRole"
            >
              <b-form-radio value="user">Пользователь</b-form-radio>
              <b-form-radio value="admin">Администратор</b-form-radio>
            </b-form-radio-group>
          </b-form-group>
        </form>
      </template>
    </CustomModalComponent>
  </div>
</template>
<script>

import CustomModalComponent from "./CustomModalComponent.vue";
import CRUDService from "@/services/CRUDService";

export default {
  name: "StudentModalComponent",
  components: {
    CustomModalComponent
  },
  data() {
    return {
      selectRole: "user",
      data: {
        login: "",
        password: ""
      }
    }
  },
  props: {
    title: {
      type: String,
      default: "Редактирование записи"
    },
    submitHandler: {
      type: Function,
      default: function () { }
    },
    crud: {
      type: CRUDService,
      required: true
    }
  },
  methods: {
    show(data) {
      if (data !== undefined) {
        this.data.password = "lolkek";
        this.data.login = data.login;
      } else {
        this.data.password = "";
        this.data.login = "";
      }

      this.$refs["custom-modal"].show(this.data);
    },

    close() {
      this.$refs["custom-modal"].close();
    },

    submit: function (data, modalEvent) {
      modalEvent.preventDefault();
      if (this.isValid) {
        this.data.isAdmin = this.selectRole == "admin";
        this.submitHandler(this.data, modalEvent);
      }
    },
    validateName(str) {
      let res = /[a-zA-Z_0-9]+/.exec(str);
      return res != null && res.length == 1 && res[0] == str ? null : false;
    }
  },
  computed: {
    loginState() {
      return this.badUsernameMessage == null && this.validateName(this.data.login);
    },
    passwordState() {
      return this.badPasswordMessage == null && 8 < this.data.password.length && this.data.password.length < 40 ? null : false;
    },
    isValid() {
      return this.loginState == null
        && this.emailState == null
        && this.passwordState == null;
    },
  }
}
</script>
<style></style>