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
  <div class="qr-content" style="text-align: center;">
  <img src="/src/assets/img/qr.svg" alt="qr_image" class="qr-img" v-if="!isQR">
  <h5 class="josefin-sans-light qr-text" style="color: #BBBDBE; font-size: 24px;" v-if="!isQR">Genera un código para acceder a la Residencia</h5>
  <img src="/src/assets/img/qr-active.svg" alt="qr_image_active" v-if="isQR" >
  </div>
</div>

<div class="d-flex justify-center">
  <v-btn class="josefin-sans btn-generar" style="margin-top: 3em; margin-bottom: 1em;" @click="generarQR" v-if="!isQR">
    <span style="text-transform: none; font-size: 18px;" class="jostfin-sans">Generar</span>
    </v-btn>
    <span class="josefin-sans-light" v-if="isQR" style="margin-top: 1.5em; margin-bottom: 1em; font-size: 26px;">30:00</span>
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
    isQRActive: true,
    isQR: false,
  }
},
methods: {
  toggle(buttonType) {
    this.isQRActive = buttonType === 'QR';
  },
  generarQR() {
    this.isQR = true
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
  height: 40px;
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
  height: 40px;
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

.qr-content{
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  
}

.qr-img{
  width: 125px;
  height: 125px;
}

.qr-text{
  width: 249px;
  margin-top: 2rem;
  line-height: normal;
}

 

@media (max-width: 769px) {
  .qr-div {
    height: 328px;
    width: 328px;
  }

  .qr-img {
    width: 125px;
    height: 125px;
  }


}
</style>
