<template>
  <div>
    <CustomModalComponent
      ref="custom-modal"
      title="Подтверждение удаления записи"
      :okHandler="okHandler"
    >
      <template v-slot:body>
        <p>Вы действительно хотите удалить запись о {{ content }}?</p>
      </template>
    </CustomModalComponent>
  </div>
</template>
<script>
import CustomModalComponent from "./CustomModalComponent.vue"

export default {
  name: "RemoveModalComponent",
  components: {
    CustomModalComponent
  },
  data() {
    return {
      result: false,
      content: ""
    }
  },
  props: {
    contentPresenter: Function,
    okHandler: Function
  },
  methods: {
    show(data) {
      this.content = this.contentPresenter(data);
      this.$refs["custom-modal"].show(data);
    },
    close() {
      this.$refs["custom-modal"].close();
    }
  }
}
</script>
<style></style>