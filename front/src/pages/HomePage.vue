<template>
  <b-card no-body>
    <b-tabs
      card
      fill
    >
      <template #tabs-end>
        <li
          role="presentation"
          class="nav-item align-self-center"
        >
          <div>
            <span>
              {{ user.username }}
            </span>
            <b-button
              size="sm"
              variant="primary"
              @click="close"
            >Выход</b-button>
          </div>
        </li>
      </template>

      <b-tab
        title="Поставки"
        lazy
      >
        <SuppliesView
          :user="user"
          :crud="crud"
          :messageService="messageService"
        />
      </b-tab>
      <b-tab
        title="Цены"
        lazy
      >
        <SuppliedDetailsView
          :user="user"
          :crud="crud"
          :messageService="messageService"
        />
      </b-tab>
      <b-tab
        title="Детали"
        lazy
      >
        <DetailsView
          :user="user"
          :crud="crud"
          :messageService="messageService"
        />
      </b-tab>
      <b-tab
        title="Поставщики"
        lazy
      >
        <SuppliersView
          :user="user"
          :crud="crud"
          :messageService="messageService"
        />
      </b-tab>
      <b-tab
        v-if="user.isAdmin == 'true'"
        title="Пользователи"
        lazy
      >
        <UsersView
          :user="user"
          :crud="crud"
          :messageService="messageService"
        />
      </b-tab>
    </b-tabs>
  </b-card>
</template>

<script>
import SuppliesView from "@/views/SuppliesView.vue"
import DetailsView from "@/views/DetailsView.vue"
import SuppliersView from "@/views/SuppliersView.vue"
import UsersView from "@/views/UsersView.vue"
import SuppliedDetailsView from "@/views/SuppliedDetailsView.vue"
import MessageService from "@/services/MessageService"
import CRUDService from "@/services/CRUDService"

export default {
  name: "HomePage",
  components: {
    SuppliesView,
    DetailsView,
    SuppliersView,
    UsersView,
    SuppliedDetailsView
  },
  data() {
    document.title = "SAD Home";
    return {
      yearsActive: true,
      classesActive: false,
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
    close() {
      this.$emit("closed");
    }
  }
}
</script>

<style>
.lol {
  border: 1px solid;
  border-color: #fff #dee2e6 #dee2e6;
  border-bottom-left-radius: 0.25rem;
  border-bottom-right-radius: 0.25rem;
  padding-top: 1rem;
}
</style>