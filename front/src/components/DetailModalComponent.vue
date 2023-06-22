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
            invalid-feedback="Название не может быть пустым"
            :state="nameState"
          >
            <b-form-input
              id="name-input"
              debounce="500"
              v-model="data.name"
              :state="nameState"
              required
            ></b-form-input>
          </b-form-group>

          <b-form-group
            label="Артикул"
            label-for="code-input"
            invalid-feedback="Не может быть пустым"
            :state="codeState"
          >
            <b-form-input
              id="code-input"
              debounce="500"
              v-model="data.partNumber"
              :state="codeState"
              required
            ></b-form-input>
          </b-form-group>

          <b-form-group
            label="Примечание"
            label-for="note-input"
            invalid-feedback="Ошибка в примечании"
            :state="noteState"
          >
            <b-form-input
              id="note-input"
              debounce="500"
              v-model="data.note"
              :state="noteState"
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
import CRUDService from "@/services/CRUDService";

export default {
  name: "DetailModalComponent",
  components: {
    CustomModalComponent
  },
  data() {
    return {
      data: {
        id: "",
        entityId: "",
        name: null,
        note: null,
        partNumber: null
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
    },
  },
  methods: {
    show(data) {
      if (data !== undefined) {
        this.data.id = data.id;
        this.data.entityId = data.entityId;
        this.data.name = data.name;
        this.data.partNumber = data.partNumber;
        this.data.note = data.note;
      }
      else {
        this.data.id = "";
        this.data.entityId = "";
        this.data.name = "";
        this.data.partNumber = "";
        this.data.note = "";
      }
      this.$refs["custom-modal"].show(this.data);
    },

    close() {
      this.$refs["custom-modal"].close();
    },


   
    isEmpty(str) {
      return str == null || str == undefined || str.trim() != "";
    },

    submit: function (data, modalEvent) {
      modalEvent.preventDefault();
      if (this.isValid) {
        // this.data.teacher = this.teachers.find(item => item.id == this.teacherId);
        // this.data.studyYear = this.years.find(item => item.id == this.studyYearId);
        // this.data.parallel = this.parallels.find(item => item.id == this.parallelId);
        this.submitHandler(this.data, modalEvent);
      }
    }
  },
  computed: {
    nameState() {
      return this.isEmpty(this.data.name) ? null : false;
    },
    noteState() {
      return null;
    },
    codeState() {
      return this.isEmpty(this.data.partNumber) ? null : false;
    },
    isValid() {
      return this.nameState == null && this.noteState == null && this.codeState == null;
    },
  }
}
</script>
<style></style>