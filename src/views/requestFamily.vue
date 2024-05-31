<template>
<Navbar />

<div class="d-flex justify-center"  style=" margin-left: 10px;">
    <div class="centered-container">

    <h3 class="josefin-sans" style="color: #000; font-size: 36px; margin-top: 1em;">Solicitudes de tu familia </h3>
    <v-btn class="request-aprove" style="height: 52px;"> 
    <img src="/src/assets/img/aproval.svg" class="aproval-icon"/>
    <span style="text-transform: none; font-size: 19px; margin-right: 2em;" class="josefin-sans"> Solicitudes aprobadas</span> </v-btn>
</div>

</div>

<div class="card-container">
    <UserCard
      v-for="(user, index) in users"
      :key="index"
      :name="user.name"
      :date="user.date"
      :relatedPerson="user.relatedPerson"
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

<style>
.request-aprove{
    width: 280px;
    height: 62px;
    margin-bottom: 1em;
    background-color: #12453B;
    border-radius: 20px;
    box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
    color: #EDF1F4;
}

.centered-container {
  display: flex;
  align-items: end;
  gap: 20px; 
}


.aproval-icon{
position: absolute;
  right: 15px;
  top: 50%;
  transform: translateY(-50%);
  height: 20px;
}

.card-container{
    display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}

@media (max-width: 769px) {
  .centered-container {
    flex-direction: column;
    justify-content: center;
  }


  .request-aprove{
    margin: auto;
  }
}
</style>