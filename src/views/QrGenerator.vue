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

<div class="d-flex justify-center qr-div" v-if="isQRActive">
  <div class="qr-content" style="text-align: center;">
  <img src="/src/assets/img/qr.svg" alt="qr_image" class="qr-img" v-if="!isQR">
  <h5 class="josefin-sans-light qr-text" style="color: #BBBDBE; font-size: 24px;" v-if="!isQR">Genera un código para acceder a la Residencia</h5>
  <img src="/src/assets/img/qr-active.svg" alt="qr_image_active" v-if="isQR" >
  </div>
</div>

<div class="d-flex justify-center" v-if="isQRActive">
  <v-btn class="josefin-sans btn-generar" style="margin-top: 3em; margin-bottom: 1em;" @click="generarQR" v-if="!isQR">
    <span style="text-transform: none; font-size: 18px;" class="jostfin-sans">Generar</span>
    </v-btn>
    <span class="josefin-sans-light" v-if="isQR" style="margin-top: 1.5em; margin-bottom: 1em; font-size: 26px; color: #171616;">30:00</span>
</div> 

<div class="d-flex justify-center" v-if="!isQRActive">
<h3 class="josefin-sans" style="color: #000; font-size: 36px; margin-top: 1em;" id="Form-title">Solicitud de acceso para visitantes </h3>
</div>

<div class="d-flex flex-column align-center">
<v-form v-if="!isQRActive" style="margin-top: 3em; width: 60%;">
  <v-row>
    <v-col cols="12" md="6">
      <h3 class="josefin-sans" style="color: #000;">Email</h3>
      <v-text-field placeholder="ejemplo@gmail.com" hide-details="auto" v-model="email" required class="form-label email" style="width: 300px;"></v-text-field>
    </v-col>
    <v-col cols="12" md="6">
      <h3 class="josefin-sans" style="color: #000;">DUI</h3>
      <v-text-field placeholder="000000-0"  hide-details="auto"  v-model="dui" required class="form-label" style="width: 209px"></v-text-field>
    </v-col>
  </v-row>
  <v-row>
    <v-col cols="12" md="6">
      <h3 class="josefin-sans" style="color: #000;">Tipo de entrada:</h3>
      <button :class="{'selected-button': entryType === 'única'}" @click="selectEntryType('única')"
        class="entry-button" type="button">Única</button>
      <button :class="{'selected-button': entryType === 'múltiple'}" @click="selectEntryType('múltiple')"
      class="entry-button" style="margin-left: 1em;" type="button">Multiple</button>
    </v-col>
    <v-col cols="12" md="6">
      <h3 class="josefin-sans" style="color: #000;">Hora de entrada</h3>
      <v-text-field placeholder="hh:mm aa"  hide-details="auto" v-model="entryTime" :active="menu" readonly required class="form-label" style="width: 209px;"> <img src="/src/assets/img/clock.svg" style="position: absolute; right: 15px;"/>
      <v-menu
            v-model="menu"
            :close-on-content-click="false"
            activator="parent"
            transition="scale-transition"
          >
          <v-time-picker
              v-if="menu"
              v-model="entryTime"
              full-width
            ></v-time-picker>
        </v-menu>
      </v-text-field>
    </v-col>
  </v-row>
  <v-row>
    <v-col cols="12" md="6">
      <h3 class="josefin-sans" style="color: #000;">Fechas de entrada</h3>
      <v-text-field
      v-model="entryDateFormatted"
      placeholder="Entrada"
      readonly
      :active="menu2"
      hide-details="auto"
      class="form-label"
      style="width: 202px;"
      > <img src="/src/assets/img/calendar.svg" style="position: absolute; right: 15px;">
    <v-menu  v-model="menu2"
    :close-on-content-click="false"
    activator="parent"
    transition="scale-transition">
    <v-locale-provider locale="es">
      <v-date-picker full-width v-model="entryDate" v-if="menu2">  
      </v-date-picker>
    </v-locale-provider>

    </v-menu>
    </v-text-field>
    </v-col>
    <v-col cols="12" md="6" v-if="entryType === 'múltiple'">
      <h3 class="josefin-sans" style="color: #000;">Días de entrada:</h3>
      <div class="dropdown-container">
      <div class="dropdown-days-wrapper">
        <div v-for="(day, index) in days" :key="index" class="dropdown-day" :class="['dropdown-day', { selected: selectedDays.includes(day.value) }]" v-on:click="selectDay(day.value)">
          {{ day.label }}
        </div>
      </div>
      </div>
    </v-col>
  </v-row>
</v-form>
</div>

<div class="d-flex justify-center" v-if="!isQRActive">
  <v-btn class="josefin-sans btn-generar" style="margin-top: 4em; margin-bottom: 1em;" v-if="!isQRActive">
    <span style="text-transform: none; font-size: 18px;" class="jostfin-sans">Solicitar</span>
    </v-btn>
</div> 

</template>

<script>
import Navbar from '../components/navbar.vue';
import { ref } from 'vue';
import { format } from 'date-fns';
import { es } from 'date-fns/locale';
export default {
components: {
  Navbar
},
data() {
  return {
    isQRActive: true,
    isQR: false,
    email: '',
    dui: '',
    entryType: 'única',
    entryTime: null,
    entryDate: new Date(),
    formattedDate: '',
    menu: false,
    menu2: false,
    selectedDays: [],
  }
},
methods: {
  toggle(buttonType) {
    this.isQRActive = buttonType === 'QR';
  },
  selectEntryType(type) {
      this.entryType = type;
    },
  generarQR() {
    this.isQR = true
  },
  selectDay(day) {
    const isSelected = this.selectedDays.includes(day);
  
  this.selectedDays = isSelected
    ? this.selectedDays.filter(d => d !== day)
    : [...this.selectedDays, day];

  },
},setup() {
  const days = [
      { value: 'domingo', label: 'D' },
      { value: 'lunes', label: 'L' },
      { value: 'martes', label: 'M' },
      { value: 'miércoles', label: 'M' },
      { value: 'jueves', label: 'J' },
      { value: 'viernes', label: 'V' },
      { value: 'sábado', label: 'S' }
    ];    
    
    const entryDay = ref(null);
    return {
      days,
      entryDay
    };
  },  
  computed: {
    entryDateFormatted() {
      return this.entryDate ? format(this.entryDate, 'dd/MM/yyyy', { locale: es }) : '';
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

.dropdown-days-wrapper {
  display: flex;
  flex-wrap: wrap; 
}

.dropdown-day {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 45px;
  height: 45px;
  border-radius: 50%;
  margin: 5px;
  flex-shrink: 0;
  background-color: #F6F9FB;
  color: #838383;
  font-family: 'Josefin Sans';
  font-size: 24px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.dropdown-day.selected {
  background-color: #12453B; 
  color: #F6F9FB; 
}

.dropdown-icon {
  position: absolute;
  right: 15px;
  top: 50%;
  transform: translateY(-50%);
  height: 16px;
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

.form-label{
  background-color: #F6F9FB; 
  border-radius: 8px;
  border: 1px solid #12453B;
  color: #000;
}

.entry-button {
  background-color: #F6F9FB;
  color: #696A6D;
  border: 1px solid transparent;
  padding: 0.5em 1em;
  cursor: pointer;
  border-radius: 8px;
}
.entry-button.selected-button {
  border: 1px solid #12453B;
}

#email {
  width: 300px;
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

  #Form-title{
    text-align: center;
  }

  .email {
    width: 209px !important;
  }

}
</style>
