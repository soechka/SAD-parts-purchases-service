<template>
  <div>
    <TableComponent
      ref="table"
      :fields="fields"
      v-on:itemAdd="addTableItemHandler"
      v-on:itemRemove="removeTableItemHandler"
      v-on:itemEdit="editTableItemHandler"
      :dataProvider="dataProvider"
    ></TableComponent>
    <div>
      <RemoveModalComponent
        ref="RemoveItemModal"
        :contentPresenter="value => userPresenter(value)"
        :okHandler="removeItem"
      ></RemoveModalComponent>

      <UserModalComponent
        ref="CreateItemModal"
        title="Создание записи об пользователе"
        :submitHandler="addItem"
        :crud="crud"
      >
      </UserModalComponent>

      <UserModalComponent
        ref="EditItemModal"
        title="Редактирование записи об пользователе"
        :submitHandler="updateItem"
        :crud="crud"
      >
      </UserModalComponent>
    </div>
  </div>
</template>

<script>
import TableComponent from "@/components/TableComponent.vue"
import RemoveModalComponent from "@/components/RemoveModalComponent.vue"
import UserModalComponent from "@/components/UserModalComponent.vue"
import CRUDService from "@/services/CRUDService"
import MessageService from "@/services/MessageService"

export default {
  name: "UsersView",
  components: {
    TableComponent,
    RemoveModalComponent,
    UserModalComponent
  },
  data() {
    return {
      fields: [
        {
          key: "login",
          label: "Логин"
        },
        {
          key: "isAdmin",
          label: "Роли",
          formatter: value => value ? "Администратор" : "Пользователь"
        },
        {
          key: "actions",
          label: "Действия",
          stickyColumn: true,
          editable: false,
        }
      ],
    }
  },
  props: {
    user: {
      type: Object,
      required: true
    },
    crud: {
      type: CRUDService,
      required: true
    },
    messageService: {
      type: MessageService,
      required: true
    }
  },
  methods: {
    userPresenter(user) {
      return user.login;
    },

    addTableItemHandler() {
      this.$refs["CreateItemModal"].show();
    },
    removeTableItemHandler(item) {
      this.$refs["RemoveItemModal"].show(item);
    },
    editTableItemHandler(item) {
      this.$refs["EditItemModal"].show(item);
    },

    removeItem(item, modalEvent) {
      modalEvent.preventDefault();

      this.crud.removeUser(item.id)
        .then(() => {
          this.$refs["RemoveItemModal"].close();
          this.messageService.showDelete(this.userPresenter(item));
          this.$refs["table"].update();
        })
        .catch(err => {
          this.messageService.showError(err.message);
        });
    },

    addItem(item, modalEvent) {
      modalEvent.preventDefault();

      this.crud.addUser(item)
        .then(() => {
          this.$refs["CreateItemModal"].close();
          this.messageService.showCreate(this.userPresenter(item));
          this.$refs["table"].update();
        })
        .catch(err => {
          if (err.response.message == "Error: Username is already taken!") {
            this.messageService.showError("Пользователь с таким именем уже существует");
          }
          if (err.response.data == "Password is too easy") {
            this.messageService.showError("Пароль слишком легкий, добавьте цифр и спецсимволов");
          }
          if (err.response.message == "Error: Email is already in use!") {
            this.messageService.showError("Пользователь с такой почтой уже существует");
          }
        });
    },

    updateItem(item, modalEvent) {
      modalEvent.preventDefault();

      this.crud.updateUser(item.entityId, item)
        .then(() => {
          this.$refs["EditItemModal"].close();
          this.messageService.showUpdate(this.userPresenter(item));
          this.$refs["table"].update();
        })
        .catch(err => {
          this.messageService.showError(err.message);
        });
    },

    dataProvider(ctx) {
      return this.crud.getUsers(ctx).catch(data => {
        this.messageService.showError("Не удалось получить данные");
        return data;
      });
    },
  }
}
</script>
  
<style scoped></style>
  