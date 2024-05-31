<template>
<Navbar />

<div class="d-flex justify-center">
<h3 class="josefin-sans" style="color: #000; font-size: 36px; margin-top: 1em;">Gestiòn de hogares </h3>
</div>

<div class="card-grid">
    <HouseCardComponent
      v-for="(house, index) in houses"
      :key="index"
      :houseNumber="house.houseNumber"
      :ownerName="house.ownerName"
      :address="house.address"
      :residents="house.residents"
    />
  </div>

</template>

<script>
import Navbar from '../components/navbar.vue';
import HouseCardComponent from '../components/house-card.vue';
export default {
components: {
  Navbar,
  HouseCardComponent
},
data () {
    return {
      houses: [
        { houseNumber: '40', ownerName: 'Juan Medina', address: 'Condominio los zorzalitos, Calle El Amate', residents: 4 },
        { houseNumber: '13', ownerName: 'Juan Medina', address: 'Condominio los zorzalitos, Calle El Amate', residents: 4 },
        { houseNumber: '40', ownerName: 'Juan Medina', address: 'Condominio los zorzalitos, Calle El Amate', residents: 4 },
        { houseNumber: '40', ownerName: 'Juan Medina', address: 'Condominio los zorzalitos, Calle El Amate', residents: 4 },
        { houseNumber: '13', ownerName: 'Juan Medina', address: 'Condominio los zorzalitos, Calle El Amate', residents: 4 },
        { houseNumber: '40', ownerName: 'Juan Medina', address: 'Condominio los zorzalitos, Calle El Amate', residents: 4 },
        { houseNumber: '40', ownerName: 'Juan Medina', address: 'Condominio los zorzalitos, Calle El Amate', residents: 4 },
        { houseNumber: '13', ownerName: 'Juan Medina', address: 'Condominio los zorzalitos, Calle El Amate', residents: 4 },
        { houseNumber: '40', ownerName: 'Juan Medina', address: 'Condominio los zorzalitos, Calle El Amate', residents: 4 }
      ]
}
}
}
</script>

<style scoped>
.card-grid {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 20px;
  margin-top: 2em;
}
</style>