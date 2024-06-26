<template>
    <Navbar :admin="true" />
    
    <div class="d-flex justify-center">
      <h3 class="josefin-sans titles-style" style="text-align: center; margin-top: 1em; ">Modificar un dispositivo</h3>
    </div>
    
    <div class="form-container">
      <div class="form-row">
        <div class="inputs">
        <div class="form-field-left email-text">
          <h3 class="josefin-sans field-title">Email encargado</h3>
          <v-text-field 
          bg-color=#F6F9FB
            v-model="email" 
            class="input-field"
            variant="solo"
            hide-details
            placeholder="ejemplo@gmail.com"
          ></v-text-field>
        </div>
      </div>
      <div class="form-row">
        <div class="form-field punto-text">
          <h3 class="josefin-sans field-title">Punto de acceso</h3>
          <v-select 
          bg-color=#F6F9FB
            v-model="accessPoint" 
            :items="accessPoints" 
            class="input-field"
            variant="solo"
            hide-details
          ></v-select>
        </div>
      </div>
    </div>
      <div class="form-row d-flex justify-center" style="align-items: center;">
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
}

.form-field {
  width: 50%;
  display: flex;
  flex-direction: column;
  align-items: flex-start;

}

.form-field-left {
  width: 50%;
  display: flex;
  flex-direction: column;
  align-items: flex-start;

}

.email-text{
  width: 345px;
}

.punto-text{
  width: 182px;
}

.field-title {
    color: #000;
    font-feature-settings: 'clig' off, 'liga' off;
    font-family: "Josefin Sans";
    font-size: 24px;
    font-style: normal;
    font-weight: 400;
    line-height: normal;
    margin-bottom: 0.3rem;
    margin-top: 2rem;
}

.input-field {
  width: 70%;
  border-radius: 4px;
    border: 1px solid #12453B;
    color: #12453B;
}

.create-btn {
  background-color: #12453B;
  color: white;
  font-size: 16px;
  font-weight: bold;
  text-transform: none;
  padding: 0.5em 2em;
  margin-top: 2em;
  height: 45px;
  width: 159px;
  border-radius: 20px;
}

@media (max-width: 1024px ) {
    .form-row{
      flex-direction: column;
      width: 85%;
    }

    .form-field-left{
        margin-bottom: 1em;
        width: 100%;
    }

    .form-field{
        margin-bottom: 1em;
        width: 100%;
    }

    .input-field{
      width: 95%;
    }

  .email-text{
  width: auto;
  }

  .punto-text{
    width: auto;
  }
}
</style>    