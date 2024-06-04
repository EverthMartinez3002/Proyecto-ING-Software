<template>
<Navbar />

<div class="switch-container">
    <div class="button-div">
      <button
        :class="{'Escaner-button': !isScanerActive, 'Visita-button': isScanerActive}"
        @click="toggle('Visita')"
      >Visita anónima</button>
      <button
        :class="{'Escaner-button': isScanerActive, 'Visita-button': !isScanerActive}"
        @click="toggle('Escáner')"
      >Escáner</button>
    </div>
  </div>

  <div class="d-flex justify-center" v-if="!isScanerActive">
<h3 class="josefin-sans entrie-anonymus-title" style="color: #000; font-size: 36px; margin-top: 2em; text-align: center;">Crear una entrada anónima</h3>
</div>

<div class="form-container" v-if="!isScanerActive">
    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <h3 class="josefin-sans labels" style="color: #000;">Nombre</h3>
        <input type="text" id="nombre" v-model="formData.nombre" />
      </div>
      <div class="form-group">
        <h3 class="josefin-sans labels" style="color: #000;">Comentario</h3>
        <textarea class="text-area" id="comentario" v-model="formData.comentario"></textarea>
      </div>
      <div class="d-flex justify-center">
      <v-btn class="josefin-sans btn-crear" style="margin-bottom: 1em;">
        <span style="text-transform: none; font-size: 18px;" class="jostfin-sans">Crear</span>
        </v-btn>   
    </div>
 </form>
</div>

<div class="d-flex justify-center" v-if="isScanerActive">
    <div class="scanner">
    </div>
</div>

<div class="d-flex justify-center" v-if="isScanerActive">
<v-btn class="josefin-sans btn-crear" style="margin-bottom: 1em;">
        <span style="text-transform: none; font-size: 18px;" class="jostfin-sans">Escanear</span>
        </v-btn>   
    </div>
</template>

<script>
import Navbar from '../components/navbar.vue';

export default{
    components: {
        Navbar,
    },
    data() {
        return {
            isScanerActive: false,
            formData: {
        nombre: '',
        comentario: '',
      },
        };
    },
    methods: {
        toggle(buttonType) {
        this.isScanerActive = buttonType === 'Escáner';
      },
    }
}

</script>

<style scoped>

.Visita-button {
    width: 164px;
    background-color: #F6F9FB;
    color: #2E2E2E;
    border-radius: 62px;
    height: 40px;
  }

  .Escaner-button {
    background-color: #12453B;
    color: #F6F9FB;
    border-radius: 62px;
    width: 164px;
    height: 40px;
  }
  
  .button-div {
    background-color: #F6F9FB;
    border-radius: 62px;
  }

  .switch-container {
    display: flex;
    align-items: center;
    justify-content: center;
    margin-top: 20px;
  }

  .entrie-anonymus-title{
    width: 93%;
    color: #000;
    text-align: center;
    font-feature-settings: 'clig' off, 'liga' off;
    font-family: "Josefin Sans";
    font-size: 32px;
    font-style: normal;
    font-weight: 600;
    line-height: normal;
    }

    .form-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 2em;
}

.form-group {
  margin-bottom: 1em;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.form-group label {
  margin-bottom: 0.5em;
}

.labels {
    width: 223px;
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

.form-group input,
.form-group textarea {
  width: 450px;
  padding: 0.5em;
  border: 1px solid #12453B;
  border-radius: 4px;
  background-color: #F6F9FB;
  color: #2E2E2E;
}

.text-area{
    height: 181px;
}

.btn-crear{
  color: #FFF;
  width: 169px;
  height: 48px;
  background: #12453B;
  border-radius: 20px;
  margin-top: 4em; 
}

.scanner{
    margin-top: 5em;
    width: 409px;
    height: 352px;
    border-radius: 20px;
    background: #F6F9FB;
}

@media (max-width: 1024px ) {
    .form-group {
    align-items: center;
    }

    .form-container{
        display: block;
        align-items: center;
    }

    .form-row{
        display: block;
        width: 100%;
        align-items: center;
    }

    .form-field-left{
        margin-left: 1em;
        margin-bottom: 1em;
        align-items: center;
    }

    .form-field{
        margin-bottom: 1em;
        align-items: center;
    }

    .form-group input,
    .form-group textarea {
  width: auto;
  padding: 0.5em;
  border: 1px solid #12453B;
  border-radius: 4px;
  background-color: #F6F9FB;
  color: #2E2E2E;
  }
  
  .btn-crear{
  color: #FFF;
  width: 169px;
  height: 48px;
  background: #12453B;
  border-radius: 20px;
  margin-top: 2em; 
}

.scanner{
    width: 289px;
    height: 352px;
    background: #F6F9FB;
}

}
</style>

