<template>
<v-data-table :headers="headers_type" :items="entries" item-key="id" :items-per-page="pagination.itemsPerPage"
:page.sync="pagination.page" :total-items="totalEntries" class="mt-5"
style="background-color: #EDF1F4; font-family: 'Josefin Sans', sans-serif;" @update:page="pageChanged"
@update:items-per-page="itemsPerPageChanged"
>

<template v-slot:header.nombre="{ column }">
 <span class="title">{{ column.title }}</span> 
</template>
<template v-slot:header.tipo="{ column }">
 <span class="title">{{ column.title }}</span> 
</template>
<template v-slot:header.fecha="{ column }">
 <span class="title">{{ column.title }}</span> 
</template>
<template v-slot:header.acceso="{ column }">
 <span class="title">{{ column.title }}</span> 
</template>
<template v-slot:header.hora="{ column }">
 <span class="title">{{ column.title }}</span> 
</template>
</v-data-table>
</template>
  
  <script>
  export default {
    name: 'HistoryTable',
    props: {
        entries: {
            type: Array,
            required: true
        },
        page: {
          type: Number,
          required: false
        },
        itemsPerPage: {
          type: Number,
          required: false
        },
        totalEntries: {
          type: Number,
          required: false
        }
    },
    data: () => ({
        headers_type: [],
        headers: [
        { title: 'Nombre', value: 'userName'},
        { title: 'Fecha', value: 'date' },
        { title: 'Acceso', value: 'entryType' },
        { title: 'Hora', value: 'entryTime'},
      ],
      headers_resident: [
        { title: 'Nombre', value: 'nombre'},
        { title: 'Fecha', value: 'fecha' },
        { title: 'Acceso', value: 'acceso' },
        { title: 'Hora', value: 'hora'},
      ],
      pagination: {},
      currentPage: 1,
    }),
    watch: {
    page(newVal) {
      this.pagination.page = newVal;
      this.currentPage = newVal; 
    },
    itemsPerPage(newVal) {
      this.pagination.itemsPerPage = newVal;
    }
  },
  mounted(){
      this.headers_type = this.headers;
      this.pagination = { page: this.page, itemsPerPage: this.itemsPerPage };
      this.currentPage = this.page;
  },
  methods: {
    pageChanged(newPage) {
      this.$emit('update:page', newPage);
    },
    itemsPerPageChanged(newItemsPerPage) {
      this.$emit('update:items-per-page', newItemsPerPage);
    }
  }
}
  </script>
  
  <style scoped>
  .title{
    font-size: 24px;
  }
  @media (max-width: 768px) {
  .title{
    font-size: 14px;
  }
}
  </style>
  