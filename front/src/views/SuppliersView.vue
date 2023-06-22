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
        :contentPresenter="value => supplierPresenter(value)"
        :okHandler="removeItem"
      ></RemoveModalComponent>

      <SupplierModalComponent
        ref="CreateItemModal"
        title="Создание записи о поставщике"
        :submitHandler="addItem"
      >
      </SupplierModalComponent>

      <SupplierModalComponent
        ref="EditItemModal"
        title="Редактирование записи о поставщике"
        :submitHandler="updateItem"
      >
      </SupplierModalComponent>
    </div>
  </div>
</template>
  
<script>
import TableComponent from "@/components/TableComponent.vue"
import RemoveModalComponent from "@/components/RemoveModalComponent.vue"
import SupplierModalComponent from "@/components/SupplierModalComponent.vue"
import CRUDService from "@/services/CRUDService"
import MessageService from "@/services/MessageService"

export default {
  name: "SuppliersView",
  components: {
    TableComponent,
    RemoveModalComponent,
    SupplierModalComponent
  },
  data() {
    return {
      fields: [
        {
          key: "name",
          sortable: true,
          filtrable: true,
          label: "Название"
        },
        {
          key: "address",
          sortable: true,
          filtrable: true,
          label: "Адрес",
        },
        {
          key: "phone",
          sortable: true,
          filtrable: true,
          label: "Телефон",
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
    supplierPresenter(data) {
      return data.name;
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

      this.crud.removeSupplier(item.entityId)
        .then(() => {
          this.$refs["RemoveItemModal"].close();
          this.messageService.showDelete(this.supplierPresenter(item));
          this.$refs["table"].update();
        })
        .catch(err => {
          this.messageService.showError(err.message);
        });
    },

    addItem(item, modalEvent) {
      modalEvent.preventDefault();

      this.crud.addSupplier(item)
        .then(() => {
          this.$refs["CreateItemModal"].close();
          this.messageService.showCreate(this.supplierPresenter(item));
          this.$refs["table"].update();
        })
        .catch(err => {
          this.messageService.showError(err.message);
        });
    },

    updateItem(item, modalEvent) {
      modalEvent.preventDefault();

      this.crud.updateSupplier(item.entityId, item)
        .then(() => {
          this.$refs["EditItemModal"].close();
          this.messageService.showUpdate(this.supplierPresenter(item));
          this.$refs["table"].update();
        })
        .catch(err => {
          this.messageService.showError(err.message);
        });
    },

    dataProvider(ctx) {
      return this.crud.getSuppliers(ctx).catch(data => {
        this.messageService.showError("Не удалось получить данные");
        return data;
      });
    },
  }
}
</script>
  
<style scoped></style>
  