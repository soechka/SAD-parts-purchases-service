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
        :contentPresenter="value => suppliedDetailPresenter(value)"
        :okHandler="removeItem"
      ></RemoveModalComponent>

      <SuppliedDetailModalComponent
        ref="CreateItemModal"
        title="Создание записи o цене детали"
        :submitHandler="addItem"
        :crud="crud"
      >
      </SuppliedDetailModalComponent>

      <SuppliedDetailModalComponent
        ref="EditItemModal"
        title="Редактирование записи o цене детали"
        :submitHandler="updateItem"
        :crud="crud"
      >
      </SuppliedDetailModalComponent>
    </div>
  </div>
</template>
  
<script>
import TableComponent from "@/components/TableComponent.vue"
import RemoveModalComponent from "@/components/RemoveModalComponent.vue"
import SuppliedDetailModalComponent from "@/components/SuppliedDetailModalComponent.vue"
import CRUDService from "@/services/CRUDService"
import MessageService from '@/services/MessageService'

export default {
  name: "SuppliedDetailsView",
  components: {
    TableComponent,
    RemoveModalComponent,
    SuppliedDetailModalComponent
  },
  data() {
    return {
      fields: [
        {
          key: "date",
          sortable: true,
          filtrable: false,
          label: "Дата изменения",
          formatter: value => value.split('T')[0]
        },
        {
          key: "cost",
          sortable: true,
          filtrable: false,
          label: "Цена",
        },
        {
          key: "detailData",
          // sortable: true,
          // filtrable: true,
          label: "Название детали",
          formatter: value => value.name
        },
        {
          key: "detailData",
          // sortable: true,
          // filtrable: false,
          label: "Артикул",
          formatter: value => value.partNumber
        },
        {
          key: "supplierData",
          // sortable: true,
          // filtrable: false,
          label: "Поставщик",
          formatter: value => value.name
        },
        {
          key: "actions",
          label: "Действия",
          stickyColumn: true
        }
      ],
      address: this.connection + "/students"
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
    suppliedDetailPresenter(data) {
      return data.supplierData.name + " " + data.detailData.name;
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

      this.crud.removeSuppliedDetail(item.entityId)
        .then(() => {
          this.$refs["RemoveItemModal"].close();
          this.messageService.showDelete(this.suppliedDetailPresenter(item));
          this.$refs["table"].update();
        })
        .catch(err => {
          this.messageService.showError(err.message);
        });
    },

    addItem(item, modalEvent) {
      modalEvent.preventDefault();

      this.crud.addSuppliedDetail(item)
        .then(() => {
          this.$refs["CreateItemModal"].close();
          this.messageService.showCreate(this.suppliedDetailPresenter(item));
          this.$refs["table"].update();
        })
        .catch(err => {
          this.messageService.showError(err.message);
        });
    },

    updateItem(item, modalEvent) {
      modalEvent.preventDefault();

      this.crud.updateSuppliedDetail(item.entityId, item)
        .then(() => {
          this.$refs["EditItemModal"].close();
          this.messageService.showUpdate(this.suppliedDetailPresenter(item));
          this.$refs["table"].update();
        })
        .catch(err => {
          this.messageService.showError(err.message);
        });
    },

    loadAllData(suppliedDetail){
      this.crud.getSupplier(suppliedDetail.supplier).then(item =>{
        suppliedDetail.supplierData = item;
      });

      this.crud.getDetail(suppliedDetail.part).then(item =>{
        suppliedDetail.detailData = item;
      });
      return suppliedDetail;
    },

    // async dataProvider(ctx) {
    //   var results = await this.crud.getSuppliedDetails(ctx);
    //   // for (let i = 0; i < results.length; i++) {
    //   //   // let element = results[i];
    //   //   // element.detailData = await this.crud.getDetail(element.part); 
    //   //   // element.supplierData =  await this.crud.getSupplier(element.supplier);       
    //   // }
    //   return results;
    // },

    dataProvider(ctx) {
      return this.crud.getSuppliedDetails(ctx)
      .catch(data => {
        this.messageService.showError("Не удалось получить данные");
        return data;
      });
    },
  }
}
</script>
  
<style scoped></style>
  