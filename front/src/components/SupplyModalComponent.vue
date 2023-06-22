<template>
  <div>
    <CustomModalComponent ref="custom-modal" :okHandler="submit" :title="title">
      <template v-slot:body>
        <form ref="form">
          <b-form-group label="Дата" label-for="date-input" invalid-feedback="Неверный формат даты">
            <b-form-input id="date-input" v-model="data.date" debounce="500" type="date" required></b-form-input>
          </b-form-group>

          <b-form-group label="Поставщик" label-for="supplier-input">
            <b-form-select id="supplier-input" v-model="supplierId" :options="suppliers" text-field="text"
              value-field="entityId" required></b-form-select>
          </b-form-group>

          <b-form-group label="Поставляемая деталь" label-for="suppliedDetail-input" invalid-feedback="Нужно выбрать деталь">
            <b-form-select id="suppliedDetail-input" v-model="suppliedDetailId" :options="suppliedDetails"
              text-field="text" value-field="entityId" :state="suppliedDetailState" required></b-form-select>
          </b-form-group>

          <b-form-group label="Цена за деталь" label-for="costPerDetail-input" invalid-feedback="Неверный формат даты">
            <b-form-input id="costPerDetail-input" v-model="costPerDetail" debounce="500" type="number" readonly></b-form-input>
          </b-form-group>

          <b-form-group label="Количество" label-for="name-input" invalid-feedback="Количество должно быть больше 0">
            <b-form-input id="name-input" v-model="data.amount" placeholder="Введите количество" debounce="500"
            :state="amountState"
              type="number" required ></b-form-input>
          </b-form-group>

          <b-form-group label="Итоговая стоимость" label-for="costPerDetail-input" invalid-feedback="Неверный формат даты">
            <b-form-input id="costPerDetail-input" v-model="costAll" debounce="500" type="number" readonly></b-form-input>
          </b-form-group>

        </form>
      </template>
    </CustomModalComponent>
  </div>
</template>
<script>

import CustomModalComponent from './CustomModalComponent.vue';
import CRUDService from "@/services/CRUDService";

export default {
  name: "SupplyModalComponent",
  components: {
    CustomModalComponent
  },
  data() {
    return {
      suppliedDetailId: null,
      supplierId: null,
      suppliers: [],
      loadedSuppliedDetails: [],
      data: {
        id: null,
        date: new Date().format("yyyy-mm-dd"),
        amount: 0
      }
    }
  },
  props: {
    title: {
      type: String,
      default: 'Редактирование записи'
    },
    submitHandler: {
      type: Function,
      default: function () { }
    },
    crud: {
      type: CRUDService,
      required: true
    }
  },
  methods: {
    async show(data) {
      this.$refs["custom-modal"].show(this.data);
      await this.updateData();
      if (data !== undefined) {
        this.data.id = data.id;
        this.data.entityId = data.entityId;
        this.data.name = data.name;
        this.data.date = data.date;
        this.data.amount = data.amount;
        this.supplierId = data.suppliedDetailData.supplier;
        this.suppliedDetailId = data.suppliedParts;
      }
      else {
        this.data.id = "";
        this.data.date = new Date().format("yyyy-mm-dd");
        this.data.amount = 0;
      }
    },

    close() {
      this.$refs["custom-modal"].close();
    },

    async updateData() {
      await this.crud.getSuppliedDetails({
        sort: {
          by: "cost",
          desc: false
        },
        perPage: 100
      })
        .then(items => {
          this.loadedSuppliedDetails = items;
          this.loadedSuppliedDetails.forEach(item => item.text = item.detailData.name + " " + String(item.cost));
          if (this.suppliedDetailId == null && this.loadedSuppliedDetails.length > 0) {
            this.suppliedDetailId = this.loadedSuppliedDetails[0].entityId;
          }
        });
      
      await this.crud.getSuppliers({
        sort: {
          by: "name",
          desc: false
        },
        perPage: 100
      })
        .then(items => {
          this.suppliers = items;
          this.suppliers.forEach(item => item.text = item.name);
          if (this.supplierId == null && this.suppliers.length > 0) {
            this.supplierId = this.suppliers[0].entityId;
          }
        });
    },

    updateSuppliedData(supplierId) {
      this.suppliedDetails = this.loadedSuppliedDetails.filter(element => element.entityId == supplierId);
    },

    submit: function (data, modalEvent) {
      if (this.isValid) {
        this.data.suppliedParts = this.suppliedDetailId;
        this.data.suppliedDetailData = this.suppliedDetail;
        this.data.amount = Number(this.data.amount);
        this.submitHandler(this.data, modalEvent);
      }
    }
  },

  computed: {
    suppliedDetails: function(){
      return this.loadedSuppliedDetails.filter(element => element.supplier == this.supplierId);
    },
    suppliedDetail: function(){
      return this.suppliedDetails.find(element => element.entityId == this.suppliedDetailId);
    },
    costPerDetail: function(){
      return this.suppliedDetail == null ? 0 : this.suppliedDetail.cost;
    },
    costAll: function(){
      return this.suppliedDetail == null ? 0 : this.suppliedDetail.cost * this.data.amount;
    },
    
    amountState: function() {
      return this.data.amount > 0 && !isNaN(this.data.amount) ? null : false;
    },
    suppliedDetailState: function() {
      return this.suppliedDetail == null ? false : null;
    },
    isValid: function() {
      return this.amountState == null && this.suppliedDetailId != null;
    }
  }
}
</script>
<style></style>