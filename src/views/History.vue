<template>
<Navbar />

<div class="d-flex justify-center">
<h3 class="josefin-sans" style="color: #000; font-size: 36px; margin-top: 1em;">Historial de entradas a tu casa </h3>
</div>

<HistoryTable :entries="entries" />
</template>

<script>
import Navbar from '../components/navbar.vue';
import HistoryTable from '../components/historytable.vue';
export default {
components: {
  Navbar,
  HistoryTable
},
data() {
    return {
      entries: [
        { id: 1, nombre: 'Juan Medina', tipo: 'Residente', fecha: '12/4/2024', acceso: 'Peatonal', hora: '9:00 AM' },
        { id: 2, nombre: 'Leo Perez', tipo: 'Visitante', fecha: '12/4/2024', acceso: 'Vehicular', hora: '8:00 AM' },
        { id: 3, nombre: 'Leo Perez', tipo: 'Visitante', fecha: '12/4/2024', acceso: 'Vehicular', hora: '7:00 PM' },
        { id: 4, nombre: 'Juan Medina', tipo: 'Residente', fecha: '12/4/2024', acceso: 'Peatonal', hora: '9:00 AM' },
        { id: 5, nombre: 'Leo Perez', tipo: 'Visitante', fecha: '12/4/2024', acceso: 'Vehicular', hora: '8:00 AM' },
        { id: 6, nombre: 'Leo Perez', tipo: 'Visitante', fecha: '12/4/2024', acceso: 'Vehicular', hora: '7:00 PM' },
      ]
    }
  }
}
</script>

<style scoped>
@media (max-width: 768px ) {
  h3{
    text-align: center;
  }
}
</style>