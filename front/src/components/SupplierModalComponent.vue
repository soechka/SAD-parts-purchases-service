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
            label="Название"
            label-for="name-input"
            invalid-feedback="Не может быть пустым"
            :state="nameState"
          >
            <b-form-input
              id="name-input"
              v-model="data.name"
              :state="nameState"
              placeholder="Введите название"
              debounce="500"
              required
            ></b-form-input>
          </b-form-group>

          <b-form-group
            label="Адрес"
            label-for="name-input"
            invalid-feedback="Не может быть пустым"
            :state="addressState"
          >
            <b-form-input
              id="name-input"
              v-model="data.address"
              :state="addressState"
              placeholder="Введите адрес"
              debounce="500"
              required
            ></b-form-input>
          </b-form-group>

          <b-form-group
            label="Телефон"
            label-for="name-input"
            invalid-feedback="Неверный формат. Пример: 89999999999"
            :state="phoneState"
          >
            <b-form-input
              id="name-input"
              type="tel"
              v-model="data.phone"
              :state="phoneState"
              placeholder="Введите телефон"
              debounce="500"
              required
            ></b-form-input>
          </b-form-group>
        </form>
      </template>
    </CustomModalComponent>
  </div>
</template>
<script>
import CustomModalComponent from "./CustomModalComponent.vue";

export default {
  name: "SupplierModalComponent",
  components: { CustomModalComponent },
  data() {
    return {
      data: {
        id: "",
        entityId: "",
        name: "",
        address: "",
        phone: ""
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
    }
  },
  methods: {
    show(data) {
      if (data !== undefined) {
        this.data.id = data.id;
        this.data.entityId = data.entityId;
        this.data.name = data.name;
        this.data.address = data.address;
        this.data.phone = data.phone;
      }
      else {
        this.data.id = "";
        this.data.entityId = "";
        this.data.name = "";
        this.data.address = "";
        this.data.phone = "";
      }

      this.$refs["custom-modal"].show(this.data);
    },
    close() {
      this.$refs["custom-modal"].close();
    },
    submit(data, modalEvent) {
      modalEvent.preventDefault();
      if (this.isValid) {
        this.submitHandler(this.data, modalEvent);
      }
    },

    validateName(str) {
      return str != null && str != "" ? null : false;
    },

    validateTelephone(str){
      console.log(str);
      //eslint-disable-next-line 
      let e = /^((8|\+7)[\- ]?)?(\(?\d{3}\)?[\- ]?)?[\d\- ]{7,10}$/gm;
      let res = e.exec(str);
      return res != null && res[0] == str ? null : false;
    }
  },
  computed: {
    addressState() {
      return this.validateName(this.data.address);
    },
    nameState() {
      return this.validateName(this.data.name);
    },
    phoneState() {
      return this.validateTelephone(this.data.phone);
    },
    isValid() {
      return this.addressState == null && this.nameState == null && this.phoneState == null;
    }
  }
}
</script>
<style></style>