<template>
<Navbar :visitor="true" />
<div class="error-container">
    <div class="error-image">
      <img src="/src/assets/img/qr-error.svg" alt="QR Code Error" class="qr-error" />
      <img src="/src/assets/img/error.svg" alt="Error Icon" class="error-icon" />
    </div>
    <p class="error-message josefin-sans-light">
      Aún no se ha aceptado una solicitud<br>
      para que puedas ingresar
    </p>
  </div>
</template>

<script>
import Navbar from '../components/navbar.vue';
export default {
components: {
  Navbar,
},
}
</script>

<style scoped>
.error-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
  color: #2E2E2E;
  margin-top: 8em;
}

.error-image {
  position: relative;
}

.qr-error {
  width: 185px;
  height: 185px;
}

.error-icon {
  position: absolute;
  top: 45%;
  left: 75%;
  width: 91px;
  height: 91px;
}

.error-message {
  margin-top: 20px;
  font-size: 22px;
  font-weight: 400;
}

@media (max-width: 1024px ) {
    .error-container{
        margin-top: 5em;
    }
}
</style>
