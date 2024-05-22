<template>

<Navbar />

<div class="switch-container">
<div class="button-div">
  <button
  :class="{'QR-button': isQRActive, 'Visita-button': !isQRActive}"    
  @click="toggle('QR')"
  >QR</button>
  <button
  :class="{'QR-button': !isQRActive, 'Visita-button': isQRActive}"   
   @click="toggle('Visita')"
  >Visita</button>
</div>
</div>

<div class="d-flex justify-center qr-div">
  <div style="text-align: center;">
  <img src="/src/assets/img/qr.svg" alt="qr_image" class="qr-img">
  <h5 class="josefin-sans-light mt-4" style="color: #BBBDBE; font-size: 24px;">Genera un código para <br> acceder a la Residencia</h5>
  </div>
</div>

<div class="d-flex justify-center">
  <v-btn class=" btn-generar" style="margin-top: 4em; margin-bottom: 1em;">
    <span style="text-transform: none; font-size: 18px;" class="jostfin-sans">Generar</span>
    </v-btn>
</div>

</template>

<script>
import Navbar from '../components/navbar.vue';
export default {
components: {
  Navbar
},
data() {
  return {
    isQRActive: true
  }
},
methods: {
  toggle(buttonType) {
    this.isQRActive = buttonType === 'QR';
  }
}
}
</script>

<style scoped>
.img-logo {
  width: 94px;
  height: 99px;
}

.QR-button {
  background-color: #12453B;
  color: #F6F9FB;
  border-radius: 62px;
  width: 164px;
}

.btn-generar{
  color: #FFF;
  width: 169px;
  height: 48px;
  background: #12453B;
  border-radius: 20px;
}


.Visita-button {
  width: 164px;
  background-color: #F6F9FB;
  color: #2E2E2E;
  border-radius: 62px;
}

.switch-container {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 20px;
}

.button-div {
  background-color: #F6F9FB;
  border-radius: 62px;
}

.navbar {
  height: 99px;
  display: flex;
  align-items: center; 
  justify-content: space-between; 
  padding: 0 20px; 
}

.user-info {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-left: auto;
}

h4{
  display: flex;
  align-items: center;
  color: #171616;
  font-size: 24px;
  padding: 15px;
}

.d-flex {
  display: flex;
  gap: 10px;
  flex-grow: 1;
  justify-content: center;
}

.google-image{
  width: 66px;
  height: 66px;
  border-radius: var(--Flat, 66px);
}

.qr-div {
background-color: #F6F9FB; width: 426px; 
height: 425px; margin: auto; margin-top: 3em; border-radius: 30px;
}

.qr-img{
  width: 125px;
  height: 125px;
  margin-top: 5em;
}

@media (min-width: 769px) {
  .qr-div {
    height: 425px;
    width: 426px;
  }

  .qr-img {
    width: 125px;
    height: 125px;
    margin-top: 5em;
  }

  .qr-text {
    font-size: 24px;
    margin-top: 2em;
  }
}
</style>
