<template>
<Navbar :admin="true" />

<div class="d-flex justify-center">
<h3 class="josefin-sans titles-style" style="color: #000; font-size: 36px; margin-top: 1em; text-align: center;">Crear una nueva casa</h3>
</div>

<div class="d-flex justify-center mt-5 main-div">
      <form class="house-form">
        <div class="form-label">
        <div class="form-row">
          <div class="form-group">
            <h3 class="josefin-sans labels" style="color: #000;">Email encargado</h3>
            <v-text-field placeholder="ejemplo@gmail.com" variant="solo" hide-details="auto" v-model="formData.emailEncargado" bg-color=#F6F9FB required class="form-input small-input"></v-text-field>
          </div>
          <div class="form-group number-label">
            <h3 class="josefin-sans labels" style="color: #000;">Número de casa</h3>
            <v-number-input hide-details="auto" class="number-input" variant="solo" control-variant="default"  :max="100"
          :min="1" v-model="formData.numeroCasa"></v-number-input>
          </div>
        </div>
        <div class="form-row">
          <div class="form-group">
            <h3 class="josefin-sans labels" style="color: #000;">Dirección</h3>
            <v-text-field placeholder="Calle el amate, Condominio Los Azoleos"  variant="solo" hide-details="auto" v-model="formData.direccion" bg-color=#F6F9FB required class="form-input small-input"></v-text-field>
          </div>
          <div class="form-group number-label">
            <h3 class="josefin-sans labels" style="color: #000; width: 251px;">Cantidad de residentes</h3>
            <v-number-input hide-details="auto" class="number-input"  variant="solo"  control-variant="default"  :max="5"
          :min="1" v-model="formData.cantidadResidentes"></v-number-input>
          </div>
        </div>
    </div>

          <div class="d-flex justify-center">
  <v-btn class="josefin-sans btn-actualizar" style="margin-top: 1.5em; margin-bottom: 4em;" @click="newHouse()">
    <span style="text-transform: none; font-size: 18px;" class="jostfin-sans">Crear</span>
    </v-btn>
</div> 
      </form>
    </div>
</template>

<script>
import Navbar from '../components/navbar.vue';
import services from '../services';
import Swal from 'sweetalert2';

export default {
components: {
  Navbar,
},
data() {
    return {
      formData: {
        emailEncargado: '',
        numeroCasa: null,
        direccion: '',
        cantidadResidentes: null,
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
    async newHouse() {
      const houseNumber = this.formData.numeroCasa;
      const address = this.formData.direccion;
      const residentNumber = this.formData.cantidadResidentes;
      const email = this.formData.emailEncargado;
      const newHouse = await services.admin.newHouse(houseNumber, address, residentNumber, email);
      console.log(newHouse);
      if(newHouse.status === 201){
        Swal.fire({
          icon: 'success',
          title: 'Casa creada con éxito',
          showConfirmButton: false,
          timer: 2000
        });
        setTimeout(() => {
          this.$router.push(`/add-residents/${newHouse.data.data.id}`);
        }, 2000);
      } else {
        Swal.fire({
          icon: 'error',
          title: 'Error al crear la casa',
        });
      }
    }
  },
}
</script>

<style scoped>
.house-form {
  width: 900px;
  background-color: #edf1f4;

  border-radius: 15px;
}

.btn-actualizar{
  color: #FFF;
  width: 169px;
  height: 48px;
  background: #12453B;
  border-radius: 20px;
}

.form-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 1.5em;
}

.form-input {
    border-radius: 4px;
    border: 1px solid #12453B;
    color: #12453B;
    width: auto;
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
    margin-top: 2rem;
  }

.resident-row {
  display: flex;

  margin-bottom: 1em;
}

.column-group{
  display: flex;
  width: 80%;
  flex-wrap: wrap;
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
    border-radius: 4px;
    border: 1px solid #12453B;
    color: #12453B
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

.btn-container{
  display: flex;
  margin-top: 1.5rem;
}

@media (max-width: 1024px) {

    .titles-style{
      width: 85%;
    }
    .house-form{
        width: 85%;

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

  .resident-group {
    flex: none;
 width: 95%;
}

    .number-label{
        margin-left: 0;
        width: 182px;
    }
}

</style>