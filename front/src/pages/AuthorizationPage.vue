<template>
  <b-container fluid="sm">
    <b-row align-h="center" align-v="center">
      <b-col col md="7" lg="5" center>
        <b-card deck>
          <b-form-group label="Авторизация" label-size="lg" label-class="font-weight-bold pt-0" class="mb-0">
            <b-form-group label="Логин" label-for="login" label-align="left" invalid-feedback="Неверный логин"
              :state="usernameState">
              <b-form-input id="login" trim :disabled="isDisabled" :state="usernameState" v-model="login"></b-form-input>
            </b-form-group>

            <b-form-group label="Пароль" label-for="password" label-align="left" invalid-feedback="Неверный пароль"
              :state="passwordState">
              <b-form-input id="password" :disabled="isDisabled" :state="passwordState" v-model="password"
                type="password"></b-form-input>
            </b-form-group>

            <b-button :disabled="isDisabled" type="submit" variant="primary" @click="tryLogIn">Войти</b-button>
          </b-form-group>
          <div v-if="this.timer > 0">
            <p>Вы исчерпали количество попыток на авторизацию</p>
            <a>Времени до следующей попытки осталось {{ timer / 60 >> 0 }}:{{ timer % 60 < 10 ? "0" + String(timer % 60) :
              timer % 60 }}</a>
          </div>
        </b-card>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import MessageService from "@/services/MessageService";
import axios from "axios"

export default {
  name: "AuthorizationPage",
  data() {
    document.title = "SAD Auth";

    var timer = this.$cookie.get('timer');
    if (timer > 0) {
      this.startTimer();
    }
    return {
      login: "",
      password: "",
      isDisabled: timer > 0,
      passwordState: null,
      usernameState: null,
      maxTryCount: 3,
      timeOut: 180,
      countTry: 0,
      timer: timer,
      user: {
        accessToken: ""
      },
      endPoint: "/sad/api/login"
    }
  },
  props: {
    connection: {
      type: String,
      required: true
    },
    messageService: {
      type: MessageService,
      required: true
    }

  },
  methods: {
    tryLogIn() {
      if (this.countTry < this.maxTryCount) {

        this.countTry++;
        this.isDisabled = true;
        this.messageService.show("Авторизация", "Попытка авторизации")
        // this.isDisabled = false; 
        // this.$emit("authorized", {
        //   accessToken: "dfd",
        //   username: "Николай Смирнов",
        //   // roles: ["ROLE_ADMIN"]
        //   roles: ["ROLE_USER"]
        // });
        axios.defaults.withCredentials = true;
        let config = {
          withCredentials: true,
          header: {
            'Content-Type': 'application/x-www-form-urlencoded'
          }
        };
        let data = new FormData();
        data.append('username', this.login);
        data.append('password', this.password);

        axios.post(this.endPoint, data, config).then(response => {
          this.messageService.show("Авторизация", this.login + " успешно авторизован")
          let data = {
            username: this.login,
            isAdmin : response.data.isAdmin
          }
          console.log("userdata", data);
          this.login = "";
          this.password = "";
          this.isDisabled = false;
          console.log(response);
          this.countTry = 0;
          this.$emit("authorized", data);
        }).catch(err => {
          this.usernameState = false;
          this.passwordState = false;
          this.messageService.showError(err.message);
          this.isDisabled = false;
        });
      }
      else {
        this.isDisabled = true;
        this.timer = this.timeOut;
        this.startTimer();
      }
    },
    startTimer() {
      var timer = setInterval(
        () => {
          if (this.timer > 0) {
            this.timer--;
            this.$cookie.set('timer', this.timer, "{0}s".f(this.timer));
          }
          else {
            this.countTry = 0;
            this.isDisabled = false;
            clearTimeout(timer);
          }
        },
        1000
      );
    }
  }
}
</script>
<style></style>