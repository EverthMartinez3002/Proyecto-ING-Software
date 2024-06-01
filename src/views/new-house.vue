<template>
<Navbar />

<div class="d-flex justify-center">
<h3 class="josefin-sans" style="color: #000; font-size: 36px; margin-top: 1em; text-align: center;">Crear una nueva casa</h3>
</div>

<div class="d-flex justify-center mt-5 main-div">
      <form class="house-form">
        <div class="form-label">
        <div class="form-row">
          <div class="form-group">
            <h3 class="josefin-sans labels" style="color: #000;">Email encargado</h3>
            <v-text-field  hide-details="auto" variant="solo-filled" class="text-field" placeholder="ejemplo@gmail.com" v-model="formData.emailEncargado" ></v-text-field>
          </div>
          <div class="form-group number-label">
            <h3 class="josefin-sans labels" style="color: #000;">Número de casa</h3>
            <v-number-input hide-details="auto" class="number-input" variant="solo-filled" control-variant="default"  :max="10"
          :min="0"></v-number-input>
          </div>
        </div>
        <div class="form-row">
          <div class="form-group">
            <h3 class="josefin-sans labels" style="color: #000;">Dirección</h3>
            <v-text-field  hide-details="auto" variant="solo-filled" class="text-field" v-model="formData.direccion" outlined></v-text-field>
          </div>
          <div class="form-group number-label">
            <h3 class="josefin-sans labels" style="color: #000;">Cantidad de residentes</h3>
            <v-number-input hide-details="auto" class="number-input"  variant="solo-filled"  control-variant="default"  :max="10"
          :min="0"></v-number-input>
          </div>
        </div>
    </div>

        <div class="form-section">
          <h3 class=" d-flex justify-center josefin-sans labels" style="color: #000;">Residentes</h3>
          <div v-for="(residente, index) in formData.residentes" :key="index" class="resident-row">
            <div class="resident-group">
              <h3 class="josefin-sans labels">Email</h3>
              <v-text-field placeholder="ejemplo@gmail.com" hide-details="auto" class="text-field"  variant="solo-filled" v-model="residente.email" outlined></v-text-field>
            </div>
            <div class="resident-group">
              <h3 class="josefin-sans labels">DUI</h3>
              <v-text-field placeholder="0000000-0"  hide-details="auto" class="text-field"  variant="solo-filled" v-model="residente.dui" outlined></v-text-field>
            </div>
            <div class="btn-container">
            <v-btn icon @click="addResidente" class="buttons" :disabled="formData.residentes.length >= 3" style="margin-right: 1em;">
                <v-icon>mdi-plus</v-icon>
            </v-btn>
            <v-btn icon @click="removeResidente(index)"  :disabled="formData.residentes.length === 1" class="buttons">
                <v-icon>mdi-minus</v-icon>
            </v-btn>
            </div>
          </div>
        </div>
      </form>
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
      formData: {
        emailEncargado: '',
        numeroCasa: '',
        direccion: '',
        cantidadResidentes: '',
        residentes: [{ email: '', dui: '' }],
      },
    };
  },
methods: {
    addResidente() {
        if (this.formData.residentes.length < 3) {
        this.formData.residentes.push({ email: '', dui: '' });
      }
    },
    removeResidente(index) {
        if (this.formData.residentes.length > 1) {
        this.formData.residentes.splice(index, 1);
      }
    },
  },
}
</script>

<style scoped>
.house-form {
  width: 900px;
  background-color: #edf1f4;
  padding: 2em;
  border-radius: 15px;
}

.form-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 1.5em;
}

.form-group {
  flex: 1;
  margin-right: 1em;
}

.resident-group {
  flex: 1;
  margin-right: 1em;
}

.form-group:last-child {
  margin-right: 0;
}

.form-section {
  margin-top: 2em;
}

.labels {
    color: #000;
    font-feature-settings: 'clig' off, 'liga' off;
    font-family: "Josefin Sans";
    font-size: 24px;
    font-style: normal;
    font-weight: 400;
    line-height: normal;
    margin-bottom: 0.3rem;
    margin-top: 1rem;
  }

.resident-row {
  display: flex;
  align-items: center;
  margin-bottom: 1em;
}

.resident-row v-text-field {
  flex: 1;
  margin-right: 1em;
}

.resident-row v-btn {
  margin-left: 0.5em;
}

.buttons {
    min-width: 36px;
  min-height: 36px;
  background-color: #12453B;
  color: #EDF1F4;
  margin-top: 1.5em;
}

.number-input{
    width: 182px;
    border-radius: 8px;
    border: 1.5px solid #12453B;  
    background: #F6F9FB;
}

.number-label{
    margin-left: 13em;
}

.text-field{
    width: 345px;
    background: #F6F9FB;
    border-radius: 8px;
    border: 1.5px solid #12453B;
}

@media (max-width: 1024px) {
    .house-form{
        width: auto;
        padding: 0em 3em 0 1em;
    }

    .number-input{
        width: auto;
        margin-left: 0;
    }

    .text-field{
        width: auto;
    }

    .form-row {
     margin-bottom: 1.5em;
     display: block;
    }

    .main-div{
        display: block;
    }

    .number-label{
        margin-left: 0;
        width: 182px;
    }
}

</style>