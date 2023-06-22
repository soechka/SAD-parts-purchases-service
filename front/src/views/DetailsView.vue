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
        :contentPresenter="value => detailPresenter(value)"
        :okHandler="removeItem"
      ></RemoveModalComponent>

      <DetailModalComponent
        :crud="crud"
        ref="CreateItemModal"
        title="Создание записи o детали"
        :submitHandler="addItem"
      >
      </DetailModalComponent>
      <DetailModalComponent
        :crud="crud"
        ref="EditItemModal"
        title="Редактирование записи o детали"
        :submitHandler="updateItem"
      >

      </DetailModalComponent>
    </div>
  </div>
</template>
  
<script>
import TableComponent from "@/components/TableComponent.vue"
import RemoveModalComponent from "@/components/RemoveModalComponent.vue"
import DetailModalComponent from "@/components/DetailModalComponent.vue"
import CRUDService from "@/services/CRUDService.js"
import MessageService from "@/services/MessageService"

export default {
  name: "DetailsView",
  components: {
    TableComponent,
    RemoveModalComponent,
    DetailModalComponent
  },
  data() {
    return {
      fields: [
        {
          key: "name",
          sortable: true,
          label: "Название",
          filtrable: true,
        },
        {
          key: "partNumber",
          sortable: true,
          filtrable: true,
          label: "Артикул"
        },
        {
          key: "note",
          sortable: true,
          filtrable: true,
          label: "Примечание",
        },
        {
          key: "actions",
          label: "Действия",
          stickyColumn: true
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
    detailPresenter(detail){
      return detail.name + " " + detail.partNumber;
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

      this.crud.removeDetail(item.entityId)
        .then(() => {
          this.$refs["RemoveItemModal"].close();
          this.messageService.showDelete(this.detailPresenter(item));
          this.$refs["table"].update();
        })
        .catch(err => {
          this.messageService.showError(err.message);
        });
    },

    addItem(item, modalEvent) {
      modalEvent.preventDefault();

      this.crud.addDetail(item)
        .then(() => {
          this.$refs["CreateItemModal"].close();
          this.messageService.showCreate(this.detailPresenter(item));
          this.$refs["table"].update();
        })
        .catch(err => {
          this.messageService.showError(err.message);
        });
    },

    updateItem(item, modalEvent) {
      modalEvent.preventDefault();

      this.crud.updateDetail(item.entityId, item)
        .then(() => {
          this.$refs["EditItemModal"].close();
          this.messageService.showUpdate(this.detailPresenter(item));
          this.$refs["table"].update();
        })
        .catch(err => {
          this.messageService.showError(err.message);
        });
    },

    dataProvider(ctx) {
      return this.crud.getDetails(ctx)
      .catch(data => {
        this.messageService.showError("Не удалось получить данные");
        return data;
      });
    },
  }
}
</script>
  
<style scoped></style>
  