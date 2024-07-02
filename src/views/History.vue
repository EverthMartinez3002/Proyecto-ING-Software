<template>
<Navbar v-if="storedRole === 'resident-admin'" :residentAdmin="true" />
<Navbar v-if="storedRole === 'resident'" :resident="true" />
<Navbar v-if="storedRole === 'visitor'" :visitor="true" />

<div class="d-flex justify-center">
<h3 class="josefin-sans history-title" style="" v-if="storedRole === 'resident-admin'">Historial de entradas a tu casa </h3>
<h3 class="josefin-sans history-title" style="" v-if="storedRole === 'resident'">Historial de tus entradas </h3>
<h3 class="josefin-sans history-title" style="" v-if="storedRole === 'visitor'">Historial de tus entradas </h3>

</div>

<HistoryTable v-if="storedRole === 'resident-admin'" :entries="entries" :page="page" :itemsPerPage="itemsPerPage" :totalEntries="totalEntries"
@update:page="handlePageChange" @update:items-per-page="handleItemsPerPageChange"/>
<HistoryTable v-if="storedRole === 'resident'" :entries="entries" :itemsPerPage="itemsPerPage" :totalEntries="totalEntries"  
@update:page="handlePageChange" @update:items-per-page="handleItemsPerPageChange"/>
<HistoryTable v-if="storedRole === 'visitor'" :entries="entries_visitor"  />

</template>

<script>
import Navbar from '../components/navbar.vue';
import HistoryTable from '../components/historytable.vue';
import { jwtDecode } from 'jwt-decode';
import services from '../services';
export default {
components: {
  Navbar,
  HistoryTable
},
data() {
    return {
      entries: [
      ],
      entries_resident: [
      ],
      entries_visitor: [
        { id: 1, nombre: 'Daniel Pérez', fecha: '12/4/2024', acceso: 'Peatonal', hora: '9:00 AM' },
        { id: 2, nombre: 'Daniel Pérez', fecha: '12/4/2024', acceso: 'Vehicular', hora: '8:00 AM' },
        { id: 3, nombre: 'Daniel Pérez',fecha: '12/4/2024', acceso: 'Vehicular', hora: '7:00 PM' },
        { id: 4, nombre: 'Daniel Pérez',fecha: '12/4/2024', acceso: 'Peatonal', hora: '9:00 AM' },
        { id: 5, nombre: 'Daniel Pérez', fecha: '12/4/2024', acceso: 'Vehicular', hora: '8:00 AM' },
        { id: 6, nombre: 'Daniel Pérez', fecha: '12/4/2024', acceso: 'Vehicular', hora: '7:00 PM' },
      ],
      storedRole: '',
      page: 1,
      itemsPerPage: 10,
      totalEntries: 0
    }
  },
  methods: {
    async getHistoryByHouse() {
      const getHistory = await services.residentAdmin.getHistoryByHouse(this.page, this.itemsPerPage);
      this.entries = this.translateEntries(getHistory.data.data.content);
      this.totalEntries = getHistory.data.data.totalElements;
    },
    translateEntries(entries) {
      const translationMap = {
        anonymous: 'Anónimo',
        pedestrian: 'Peatonal', 
        vehicle: 'Vehicular'
      };
      return entries.map(entry => {
        return {
          ...entry,
          entryType: translationMap[entry.entryType] || entry.entryType
        };
      });
    },
    handlePageChange(newPage) {
      const storedRole = localStorage.getItem('token');
      const decoded = jwtDecode(storedRole);
      this.page = newPage;
      if (decoded.roles.includes('ROLE_main resident')){
      this.getHistoryByHouse();
      }
      if (decoded.roles.includes('ROLE_resident')){
      this.getHistoryByUser();
      }
    },
    handleItemsPerPageChange(newItemsPerPage) {
      const storedRole = localStorage.getItem('token');
      const decoded = jwtDecode(storedRole);
      this.itemsPerPage = newItemsPerPage;
      if (decoded.roles.includes('ROLE_main resident')){
      this.getHistoryByHouse();
      }
      if (decoded.roles.includes('ROLE_resident')){
      this.getHistoryByUser();
      }
    },
    async getHistoryByUser() {
      const getHistoryByUser = await services.resident.getAllHistoryEntriesbyUser(this.page, this.itemsPerPage);
      this.entries = this.translateEntries(getHistoryByUser.data.data.content);
      this.totalEntries = getHistoryByUser.data.data.totalElements;
    }
  },
  created(){
    const storedRole = localStorage.getItem('token');
    const decoded = jwtDecode(storedRole);
    if (decoded.roles.includes('ROLE_main resident')){
    this.getHistoryByHouse();
    }
    if (decoded.roles.includes('ROLE_resident')){
      this.getHistoryByUser();
    }
  },
  mounted(){
     const storedRole = localStorage.getItem('token');
      const decoded = jwtDecode(storedRole);
      if (decoded.roles.includes('ROLE_main resident')){
        this.storedRole = 'resident-admin';
      }
      if (decoded.roles.includes('ROLE_resident')){
        this.storedRole = 'resident';
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