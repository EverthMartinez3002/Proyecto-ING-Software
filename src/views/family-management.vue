<template>
<Navbar />

<div class="d-flex justify-center">
<h3 class="josefin-sans" style="color: #000; font-size: 36px; margin-top: 1em;">Gestión de residentes de tu casa</h3>
</div>

<div class="d-flex justify-center form-container">
    <div v-for="(resident, index) in residents" :key="index" class="resident-form">
      <div class="field-container">
    <h3 class="josefin-sans" style="color: #000;">Email*</h3>
      <v-text-field
        v-model="resident.email"
        hide-details="auto"
        placeholder="cmedina@gmail.com"
        class="email-field"
        style="width: 300px; color: #000;"
      ></v-text-field>
      </div>
      <div class="field-container">
        <h3 class="josefin-sans" style="color: #000;">DUI</h3>
      <v-text-field
        v-model="resident.dui"
        hide-details="auto" 
        placeholder="312312345-4"
        class="dui-field"
        style="color: #000;"
      ></v-text-field>
      </div>
      <div class="btn-container">
      <v-btn icon @click="addResident" class="add-btn" :disabled="residents.length >= 3">
        <v-icon>mdi-plus</v-icon>
      </v-btn>
      <v-btn icon @click="removeResident(index)" class="remove-btn" :disabled="residents.length <= 1">
        <v-icon>mdi-minus</v-icon>
      </v-btn>
    </div>
    </div>
  </div>

  <div class="d-flex justify-center">
  <v-btn class="josefin-sans btn-actualizar" style="margin-top: 4em; margin-bottom: 1em;" v-if="!isQRActive">
    <span style="text-transform: none; font-size: 18px;" class="jostfin-sans">Actualizar</span>
    </v-btn>
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
      residents: [
        { email: '', dui: '' },
        { email: '', dui: '' }
      ]
    };
  }
,
methods: {
    addResident() {
        if (this.residents.length < 3) {
            this.residents.push({ email: '', dui: '' });
        }    
    },
    removeResident(index) {
        if (this.residents.length > 1) {
            this.residents.splice(index, 1);
        }
    }
  },

}
</script>

<style scoped>

.field-container {
  display: flex;
  flex-direction: column;
}

.form-container{
    flex-direction: column;
    width: 50%;
    margin: auto;
    margin-top: 3em;
}

.resident-form {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 1em;
}

.email-field{
   width: 300px;
   border-radius: 8px;
   background-color: white;
   border: 1px solid #12453B;
}

.dui-field{
    width: 209px;
    border-radius: 8px;
    background-color: white;
    border: 1px solid #12453B;
}

.add-btn,
.remove-btn {
  min-width: 36px;
  min-height: 36px;
  background-color: #12453B;
  color: #EDF1F4;
  margin-top: 1.5em;
}

.update-btn {
  width: 150px;
  height: 45px;
  margin-top: 1em;
  background-color: #12453B;
  color: #EDF1F4;
  border-radius: 10px;
}

.btn-actualizar{
  color: #FFF;
  width: 169px;
  height: 48px;
  background: #12453B;
  border-radius: 20px;
}

.remove-btn {
    margin-left: 0.5em;
  }

@media (max-width: 768px ) {
  .resident-form {
    flex-direction: column;
    align-items: center;
    
  }

  .email-field {
    width: 100%;
  }

  .dui-field{
    margin-right: 5.5em;
  }

  .add-btn,
  .remove-btn {
    align-self: center;
  }

  .remove-btn {
    margin-left: 1em;
  }

  .btn-container {
    flex-direction: column;
  }
}
</style>