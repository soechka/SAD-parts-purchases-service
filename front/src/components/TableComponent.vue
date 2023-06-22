<template>
  <b-container fluid>
    <b-row>
      <b-col cols="auto">
        <b-form-group
          label="Отображать по"
          label-for="per-page-select"
          label-cols="auto"
          label-align-sm="left"
          label-size="sm"
        >
          <b-form-select
            id="per-page-select"
            v-model="pContext.perPage"
            :options="pPageOptions"
            size="sm"
          ></b-form-select>
        </b-form-group>
      </b-col>

      <b-col></b-col>

      <b-col>
        <div align="end">
          <b-button
            size="sm"
            variant="primary"
            @click="addItem"
          >Добавить</b-button>
        </div>
      </b-col>
    </b-row>

    <!--  :sticky-header="true"
          :no-border-collapse="true"
           -->
    <b-table
      striped
      hover
      bordered
      show-empty
      :filter="pFilter"
      :items="getItems"
      :fields="fields"
      :current-page="pContext.currentPage"
      :per-page="pContext.perPage"
      :sort-by.sync="pContext.sort.by"
      :sort-desc.sync="pContext.sort.desc"
    >
      <template #cell(actions)="data">
        <div>
          <b-button
            v-if="data.field.editable == true || data.field.editable == undefined"
            class="actionButton"
            variant="primary"
            @click="editItem(data.item)"
            size="sm"
          > <b-icon icon="pencil-square"></b-icon>
          </b-button>
          <b-button
            v-if="data.field.removable == true || data.field.removable == undefined"
            @click="removeItem(data.item)"
            variant="danger"
            size="sm"
          >
            <b-icon icon="trash-fill"></b-icon>
          </b-button>
        </div>
      </template>

      <template #head()="scope">
        <div>
          <div>
            <span>{{ scope.label }}</span>
          </div>

          <div v-if="scope.field.filtrable">
            <b-input-group size="sm">
              <b-form-input
                id="filter-input"
                type="search"
                placeholder="Введите для поиска"
                v-model="pContext.filters[scope.field.key]"
                @update="update"
                debounce="500"
              ></b-form-input>
            </b-input-group>
          </div>

          <div
            v-else
            class="block"
          >
          </div>
        </div>
      </template>

      <template #empty>
        <p v-if="pContext.error == null">Записи отсутствуют</p>
        <p v-else>Произошла ошибка: {{ pContext.error }}</p>
      </template>

      <template #table-busy>
        <p>Гружусь...</p>
      </template>

      <template #emptyfiltered>
      <p>По вашему запросу ничего не нашлось :(</p>
    </template>
    </b-table>

    <b-row>
      <b-col>
        <div align="start">
          <p v-if="pContext.size == 0">
            Записей нет
          </p>
          <p v-else> Отображено с {{ (pContext.currentPage - 1) * pContext.perPage + 1 }} по {{ (pContext.currentPage - 1)
            * pContext.perPage + pContext.size }} записей из {{ pContext.totalRows }} </p>
        </div>
      </b-col>
      <b-col cols="4">
        <b-pagination
          v-model="pContext.currentPage"
          :total-rows="pContext.totalRows"
          :per-page="pContext.perPage"
          align="fill"
          size="sm"
          class="my-0"
        ></b-pagination>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
export default {
  name: "TableComponent",
  data() {
    let context = {
      totalRows: 1,
      totalPages: 1,
      perPage: 5,
      currentPage: 1,
      size: 0,
      sort: {
        by: this.fields[0].key,
        desc: false,
      },
      filters: {},
      error: null
    };
    console.log("поля", this.fields);
    this.fields.forEach(field => {
      if (field.filtrable) {
        context.filters[field.key] = "";
      }
    });

    return {
      pIsBusy: false,

      pContext: context,

      pPageOptions: [5, 10, 20, 50, 100],

      pFilter: null,
    }
  },

  props: {
    fields: Array,
    dataProvider: Function
  },

  computed: {
    isBusy: {
      get: function () {
        return this.pIsBusy;
      },
      set: function (newValue) {
        this.pIsBusy = newValue;
      }
    },
    totalRows: function () {
      return this.pContext.totalRows;
    },
    totalPages: function () {
      return this.pContext.totalPages;
    },
    currentPage: function () {
      return this.pContext.currentPage;
    },
    perPage: function () {
      return this.pContext.perPage;
    },
  },


  methods: {
    addItem() {
      this.$emit("itemAdd");
    },
    removeItem(item) {
      this.$emit("itemRemove", item);
    },
    editItem(item) {
      this.$emit("itemEdit", item);
    },

    update() {
      this.pIsBusy = true;
      // Костыль, чтобы обновить фильтр
      this.pFilter = this.pFilter == "a" ? "b" : "a";
      this.pIsBusy = false;
    },
    getItems() {
      this.pIsBusy = true;
      this.pContext.error = null;
      let data = this.dataProvider(this.pContext);
      console.log("lolkek", data);
      this.pIsBusy = false;
      return data;
    },
  },
}
</script>

<style>
.block {
  height: 31px;
}

.actionButton {
  margin-right: 10px;
}
</style>
