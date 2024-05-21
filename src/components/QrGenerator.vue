<template>
<div class="navbar">
  <img src="/src/assets/img/logo_HLVS.png" alt="logo_HLVS" class="img-logo">


  <div class="d-flex">
  <h4 class="josefin-sans-light">Familia</h4>
  <h4 class="josefin-sans-light">Generar QR</h4>
  <h4 class="josefin-sans-light">Solicitudes</h4>
  <h4 class="josefin-sans-light">Historial</h4>
  </div>

  <div class="user-info">
  <h4 class="josefin-sans-light">Duglas Pineda</h4>
  <img src="/src/assets/img/google-image.png" alt="google_image" class="google-image">
  </div>
</div>


<div class="switch-container">
<div class="button-div">
  <button
    class="QR-button"
    @click="toggle"
  >QR</button>
  <button
    class="Visita-button"
    @click="toggle"
  >Visita</button>
</div>
</div>

</template>


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
</style>
