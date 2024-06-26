<template>
  <div class="home-container">
  
    <div class="w-100 d-flex justify-center flex-column" style="align-items: center;">
    <img src="/src/assets/img/logo_HLVS.png" alt="logo_HLVS" class="img-logo">
  
    <h2 class="mt-5 josefin-sans" style="color: #000;">H L V S</h2>
  
    <h3 class="mt-5 josefin-sans-light" style="font-size: 22px; text-align: center; color: #171616;">Bienvenido/a al sistema de entrada de la residencial HLVS</h3>
  
    <v-btn class="button-google" style="background-color: #F1F5F9; width: 300px;" @click="redirectToQr()">
      <img src="/src/assets/img/google-icon.svg" alt="Google Icon" class="google-icon"/>
      <span style="text-transform: none;" class="inter"> Continua con Google</span></v-btn>
    </div>
  
    <div class="d-flex justify-end w-100">
    <img src="/src/assets/img/casa_alargada.png" alt="casa_logo" class="w-100">
    </div>
  
  </div>
  
  </template>
  
  <script>
  import { googleTokenLogin  } from 'vue3-google-login';
  import axios from 'axios';
  
  export default {
    methods: {
      async redirectToQr() {
     const response = await googleTokenLogin();
     let access_token = response.access_token;
     const googleLogin = await this.fetchGoogleUserData(access_token);
     if(googleLogin != null){
       this.$router.push('/qr');
     }
    },
      async fetchGoogleUserData(accessToken) {
      try {
        const response = await axios.get('https://www.googleapis.com/oauth2/v3/userinfo', {
          headers: {
            Authorization: `Bearer ${accessToken}`
          }
        });
        return response.data;
      } catch (error) {
        console.error('Error obteniendo Google user data:', error);
        return null;
      }
    }
    }
  }
  </script>
  
  <style scoped>
  .home-container{
    display: flex;
  }
  
  .img-logo {
    width: 150px;
    height: 150px;
  }
  
  .button-google{
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: var(--Semi-rounded, 12px);
    background: #F1F5F9;
    box-shadow: 8px 8px 16px 0px rgba(201, 217, 232, 0.70), -8px -8px 16px 0px #FFF;
    margin-top: 2em;
    text-transform: none;
    gap: 16px;
    padding: 23px 32px 23px 16px;
  }
  
  .google-icon {
    width: 20px;
    height: 20px;
    margin-right: 20px;
  }
  
  @media (max-width: 768px ) {
    .home-container {
      flex-wrap: wrap;
      justify-content: center;
      align-items: center;
    }
  
    .button-google {
      margin-bottom: 1.5em;
    }
  }
  </style>