<template>
  <div id="app">
    <b-container fluid="lg">
      <b-row>
        <b-col>
          <AuthorizationPage
            v-if="user == null"
            :connection="connection"
            :messageService="messageService"
            @authorized="logIn"
          ></AuthorizationPage>
          <HomePage
            v-else
            :user="user"
            :crud="crudService"
            :messageService="messageService"
            @closed="logOut"
          ></HomePage>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>


<script>

import AuthorizationPage from "./pages/AuthorizationPage.vue";
import HomePage from "./pages/HomePage.vue";
import CRUDService from "./services/CRUDService";
import MessageService from "./services/MessageService";



export default {
  name: "App",
  components: {
    AuthorizationPage,
    HomePage
  },
  data() {
    document.title = "SAD";
    return {
      user: null,
      connection: "http://192.168.43.136:8080",
      crudService: null,
      messageService: new MessageService(this.$bvToast)
    }
  },
  methods: {
    logOut() {
      this.user.accessToken = "";
      this.user.uasername = "";
      this.user.isAdmin = false;
      this.user = null;
    },
    logIn(user) {
      this.user = user;
      this.crudService = new CRUDService(this.connection, user.accessToken);
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin: 10px;
}
</style>
