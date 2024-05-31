<template>
<Navbar />

<div class="d-flex justify-center">
<h3 class="josefin-sans history-title" style="">Historial de entradas a tu casa </h3>
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

.history-title{
margin-top: 2rem;
margin-bottom: 2rem;
 width: 85%; 
color: #000;
text-align: center;
font-feature-settings: 'clig' off, 'liga' off;
font-family: "Josefin Sans";
font-size: 32px;
font-style: normal;
font-weight: 600;
line-height: normal;
}

@media (max-width: 768px ) {
  h3{
    text-align: center;
  }
}
</style>