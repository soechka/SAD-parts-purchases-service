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
        :contentPresenter="value => supplyPresenter(value)"
        :okHandler="removeItem"
      ></RemoveModalComponent>

      <SupplyModalComponent
        ref="CreateItemModal"
        title="Создание записи o поставке"
        :submitHandler="addItem"
        :crud="crud"
      >
      </SupplyModalComponent>

      <SupplyModalComponent
        ref="EditItemModal"
        title="Редактирование записи o поставке"
        :submitHandler="updateItem"
        :crud="crud"
      >
      </SupplyModalComponent>
    </div>
  </div>
</template>
  
<script>
import TableComponent from "@/components/TableComponent.vue"
import RemoveModalComponent from "@/components/RemoveModalComponent.vue"
import SupplyModalComponent from "@/components/SupplyModalComponent.vue"
import CRUDService from "@/services/CRUDService"
import MessageService from "@/services/MessageService"

export default {
  name: "SuppliesView",
  components: {
    TableComponent,
    RemoveModalComponent,
    SupplyModalComponent
  },
  data() {
    return {
      fields: [
        {
          key: "date",
          sortable: true,
          // filtrable: true,
          label: "Дата",
          formatter: value => value.split('T')[0]
        },
        {
          key: "detailData",
          // sortable: true,
          // filtrable: true,
          label: "Деталь",
          formatter: value => value.name
        },
        {
          key: "supplierData",
          // sortable: true,
          // filtrable: true,
          label: "Поставщик",
          formatter: value => value.name
        },
        {
          key: "amount",
          sortable: true,
          filtrable: true,
          label: "Количество",
        },
        {
          key: "suppliedDetailData",
          // sortable: true,
          // filtrable: true,
          label: "Цена",
          formatter: value => value.cost
        },
        {
          key: "costAll",
          sortable: false,
          filtrable: false,
          label: "Общая цена",
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
    supplyPresenter(data){
      return data.suppliedDetailData.detailData.name + " " + data.suppliedDetailData.supplierData.name;
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

      this.crud.removeSupply(item.entityId)
        .then(() => {
          this.$refs["RemoveItemModal"].close();
          this.messageService.showDelete(this.supplyPresenter(item));
          this.$refs["table"].update();
        })
        .catch(err => {
          this.messageService.showError(err.message);
        });
    },

    addItem(item, modalEvent) {
      modalEvent.preventDefault();

      this.crud.addSupply(item)
        .then(() => {
          this.$refs["CreateItemModal"].close();
          this.messageService.showCreate(this.supplyPresenter(item));
          this.$refs["table"].update();
        })
        .catch(err => {
          this.messageService.showError(err.message);
        });
    },

    updateItem(item, modalEvent) {
      modalEvent.preventDefault();

      this.crud.updateSupply(item.entityId, item)
        .then(() => {
          this.$refs["EditItemModal"].close();
          this.messageService.showUpdate(this.supplyPresenter(item));
          this.$refs["table"].update();
        })
        .catch(err => {
          this.messageService.showError(err.message);
        });
    },

    async loadAllData(supply){
      let sp = await this.crud.getSuppliedDetail(supply.suppliedParts);
      supply.detailData = await this.crud.getSupplier(sp.supplier);
      supply.supplierData = await this.crud.getDetail(sp.part).then(p => supply.detail = p);
      supply.costAll = supply.amount * sp.cost;
      return supply;
    },

    dataProvider(ctx) {
      return this.crud.getSupplies(ctx).then(items =>{
        console.log('asdfsadf', items);
        items.forEach(element => {
          element.supplierData = element.suppliedDetailData.supplierData;
          element.detailData = element.suppliedDetailData.detailData;
          element.costAll = element.amount * element.suppliedDetailData.cost;
        });
        return items;
      })
      .catch(err => {
        this.messageService.showError(err.message);
        return err;
      });
    },
  }
}
</script>
  
<style scoped></style>
  