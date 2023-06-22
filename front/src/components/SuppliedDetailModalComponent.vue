<template>
  <div>
    <CustomModalComponent ref="custom-modal" :okHandler="submit" :title="title">
      <template v-slot:body>
        <form ref="form">
          <b-form-group label="Цена" label-for="name-input" invalid-feedback="Цена должна быть больше 0" :state="costState">
            <b-form-input id="name-input" v-model="data.cost" :state="costState" placeholder="Введите цену" debounce="500"
              type="number" required></b-form-input>
          </b-form-group>

          <b-form-group label="Дата" label-for="date-input" invalid-feedback="Неверный формат даты">
            <b-form-input id="date-input" v-model="data.date" debounce="500" type="date" required></b-form-input>
          </b-form-group>

          <b-form-group label="Деталь" label-for="detail-input">
            <b-form-select id="detail-input" v-model="detailId" :options="details" text-field="text" value-field="entityId"
              required></b-form-select>
          </b-form-group>

          <b-form-group label="Поставщик" label-for="supplier-input">
            <b-form-select id="supplier-input" v-model="supplierId" :options="suppliers" text-field="text" value-field="entityId"
              required></b-form-select>
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
  name: "SuppliedDetailModalComponent",
  components: {
    CustomModalComponent
  },
  data() {
    return {
      supplierId: null,
      detailId: null,
      suppliers: [],
      details: [],

      data: {
        id: "",
        date: new Date().format("yyyy-mm-dd"),
        cost: 0,
      }
    }
  },
  mounted() {

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
      this.updateData();

      if (data !== undefined) {
        this.data.id = data.id;
        this.data.entityId = data.entityId;
        this.data.cost = data.cost;
        this.data.date = data.date;
        this.detailId = data.part;
        this.supplierId = data.supplier;
      } else {
        this.data.id = "";
        this.data.entityId = "";
        this.data.cost = 0;
        this.data.date = new Date().format("yyyy-mm-dd");
      }
      console.log(this.data.date);
      console.log(this.detailId);
      this.$refs["custom-modal"].show(this.data);
    },
    close() {
      this.$refs["custom-modal"].close();
    },

    updateData() {
      this.crud.getSuppliers({
        sort: {
          by: "name",
          desc: false
        },
        perPage: 100
      })
      .then(items => {
        this.suppliers = items;
        this.suppliers.forEach(item => item.text = item.name + " " + item.address);
        if (this.supplierId == null && this.suppliers.length > 0) {
          this.supplierId = this.suppliers[0].entityId;
        }
        console.log("updateData suppliers", this.suppliers);
      });

      this.crud.getDetails({perPage: 100})
      .then(items => {
        this.details = items;
        this.details.forEach(item => item.text = item.name + " " + item.partNumber);
        if (this.detailId == null && this.details.length > 0) {
          this.detailId = this.details[0].entityId;
        }
      });
    },

    submit: function (data, modalEvent) {
      modalEvent.preventDefault();
      if (this.isValid) {
        console.log(this.details);
        this.data.part = this.detailId;
        this.data.supplier = this.supplierId;
        this.data.supplierData = this.suppliers.find(s => s.entityId == this.supplierId);
        this.data.detailData = this.details.find(d => d.entityId == this.detailId);
        this.data.cost = Number(this.data.cost);
        this.submitHandler(this.data, modalEvent);
      }
    }
  },
  computed: {
    costState() {
      return !isNaN(this.data.cost) && this.data.cost > 0 ? null : false;
    },

    isValid() {
      return this.costState == null;
    },
  }
}
</script>
<style></style>