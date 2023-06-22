<template>
  <div>
    <b-modal
      ref="modal"
      :title="title"
      @hidden="cancelHandler"
      @ok="okHandler(modalData, $event)"
      @cancel="cancelHandler"
      ok-title="Ок"
      cancel-title="Отмена"
      :lazy=true
    >
      <slot
        name="body"
        v-bind:data="modalData"
      ></slot>
    </b-modal>
  </div>
</template>
<script>
export default {
  name: 'CustomModalComponent',
  data() {
    return {
      modalData: this.defaultData
    }
  },
  props: {
    title: {
      type: String,
      default: 'Сообщение'
    },
    okHandler: {
      type: Function,
      default: function () { }
    },
    cancelHandler: {
      type: Function,
      default: function () { }
    },
    defaultData: {
      default: () => ({})
    }
  },
  methods: {
    show(data) {
      this.modalData = data;
      this.$refs['modal'].show();
    },
    close() {
      this.$refs['modal'].hide();
    },
  },
  computed: {
    data: function () {
      return this.modalData;
    }
  }
}
</script>
<style></style>