<template>
<v-data-table :headers="headers" :items="entries" item-key="id" items-per-page="10" class="mt-5"
 style="background-color: #EDF1F4; font-family: 'Josefin Sans', sans-serif;"
>
</v-data-table>
</template>
  
  <script>
  export default {
    name: 'HistoryTable',
    props: {
        entries: {
            type: Array,
            required: true
        }
    },
    data: () => ({
        headers: [
        { title: 'Nombre', value: 'nombre'},
        { title: 'Fecha', value: 'fecha' },
        { title: 'Tipo', value: 'acceso' },
        { title: 'Hora', value: 'hora'},
      ],
    })
  }
  </script>
  
  <style scoped>
  </style>
  