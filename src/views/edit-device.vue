<template>
    <Navbar :admin="true" />
    
    <div class="d-flex justify-center">
      <h3 class="josefin-sans title" style="text-align: center;">Modificar un dispositivo</h3>
    </div>
    
    <div class="form-container">
      <div class="form-row">
        <div class="form-field">
          <h3 class="josefin-sans field-title">Número serial</h3>
          <v-text-field 
            v-model="serialNumber" 
            class="input-field"
            variant="solo"
            hide-details
          ></v-text-field>
        </div>
        <div class="form-field-left">
          <h3 class="josefin-sans field-title">Email encargado</h3>
          <v-text-field 
            v-model="email" 
            class="input-field"
            variant="solo"
            hide-details
            placeholder="ejemplo@gmail.com"
          ></v-text-field>
        </div>
      </div>
      <div class="form-row">
        <div class="form-field">
          <h3 class="josefin-sans field-title">Punto de acceso</h3>
          <v-select 
            v-model="accessPoint" 
            :items="accessPoints" 
            class="input-field"
            variant="solo"
            hide-details
          ></v-select>
        </div>
      </div>
      <div class="form-row d-flex justify-center">
        <v-btn class="create-btn">Modificar</v-btn>
      </div>
    </div>
    
    </template>
    
    <script>
    import Navbar from '../components/navbar.vue';
    
    export default {
      components: {
        Navbar,
      },
      data() {
        return {
          serialNumber: '1234567890',
          email: 'encargado@example.com',
          accessPoint: 'Pluma',
          accessPoints: ['Pluma', 'Caseta', 'Peatonal'],
        };
      },
    }
    </script>
    
    <style scoped>
    .title {
      color: #000;
      font-size: 36px;
      text-align: center;
      margin-top: 1em;
    }
    
    .form-container {
      display: flex;
      flex-direction: column;
      align-items: center;
      margin-top: 2em;
    }
    
    .form-row {
      display: flex;
      justify-content: left;
      margin-bottom: 1.5em;
      width: 50%;
    }
    
    .form-field {
      display: flex;
      flex-direction: column;
      align-items: flex-start;
      margin: 0 1em;
    }
    
    .form-field-left {
      display: flex;
      flex-direction: column;
      align-items: flex-start;
      margin: 0 1em;
      margin-left: 10em;
    }
    
    .field-title {
      color: #000;
      font-size: 18px;
      margin-bottom: 0.5em;
    }
    
    .input-field {
      width: 300px;
      background: #F6F9FB;
      border: 1px solid #12453B;
      border-radius: 8px;
    }
    
    .create-btn {
      background-color: #12453B;
      color: white;
      font-size: 16px;
      font-weight: bold;
      text-transform: none;
      padding: 0.5em 2em;
      height: 45px;
      width: 159px;
      margin-top: 3em;
      border-radius: 20px;
    }
    
    @media (max-width: 1024px) {
      .form-container {
        display: block;
      }
    
      .form-row {
        display: block;
        width: 100%;
      }
    
      .form-field-left {
        margin-left: 1em;
        margin-bottom: 1em;
        align-items: center;
      }
    
      .form-field {
        margin-bottom: 1em;
        align-items: center;
      }
    }
    </style>    