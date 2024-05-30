<template>
<Navbar />

<div class="d-flex justify-center">
<h3 class="josefin-sans" style="color: #000; font-size: 36px; margin-top: 1em;">Solicitudes aprobadas</h3>
</div>

<div class="card-container">
    <UserCard
      v-for="(user, index) in users"
      :key="index"
      :name="user.name"
      :date="user.date"
      :relatedPerson="user.relatedPerson"
      :showButton="true"
    />
</div>

</template>

<script>
import Navbar from '../components/navbar.vue';
import UserCard from '../components/request-card.vue';
export default {
components: {
  Navbar,
  UserCard
},
data() {
    return {
      users: [
        { name: 'Mirta Ramirez', date: '4/13/2024', relatedPerson: 'Cesar Medina' },
        { name: 'Alicia Alvarez', date: '4/10/2024', relatedPerson: 'Lucía Medina' }
      ]
    };
  },
}
</script>

<style scoped>

.card-container{
    display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}
</style>