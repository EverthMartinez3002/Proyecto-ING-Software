<template>

  <Navbar :residentAdmin="isAdmin" :resident="isResident" :visitor="isVisitor"/>
  
  <div class="switch-container" v-if="isAdmin || isResident">
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
      <img src="/src/assets/img/qr-active.svg" alt="qr_image_active" v-if="isQR">
    </div>
  </div>
  
  <div class="d-flex justify-center" v-if="isQRActive">
    <v-btn class="josefin-sans btn-generar" style="margin-top: 3em; margin-bottom: 1em;" @click="generarQR" v-if="!isQR">
      <span style="text-transform: none; font-size: 18px;" class="jostfin-sans">Generar</span>
    </v-btn>
    <span class="josefin-sans-light" v-if="isQR" style="margin-top: 1.5em; margin-bottom: 1em; font-size: 26px; color: #171616;">30:00</span>
  </div>
  
  <div class="d-flex justify-center" v-if="!isQRActive">
    <h3 class="josefin-sans" style="color: #000; font-size: 32px; margin-top: 1.5em; width: 80%;" id="Form-title">Solicitud de acceso para visitantes</h3>
  </div>
  
  <div class="d-flex justify-center" v-if="!isQRActive">
    <v-form class="form">
      <v-row>
        <v-col cols="12" md="6">
          <h3 class="josefin-sans labels" style="color: #000;">Email</h3>
          <v-text-field placeholder="ejemplo@gmail.com" variant="solo" hide-details="auto" v-model="email" bg-color=#F6F9FB required class="form-label small-input"></v-text-field>
        </v-col>
        <v-col cols="12" md="6">
          <h3 class="josefin-sans labels" style="color: #000;">DUI</h3>
          <v-text-field placeholder="000000-0" variant="solo" hide-details="auto" v-model="dui" bg-color=#F6F9FB required class="form-label small-input"></v-text-field>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="12" md="6">
          <h3 class="josefin-sans labels" style="color: #000;">Tipo de entrada:</h3>
          <button :class="{'selected-button': entryType === 'única'}" @click="selectEntryType('única')" class="entry-button" type="button">Única</button>
          <button :class="{'selected-button': entryType === 'múltiple'}" @click="selectEntryType('múltiple')" class="entry-button" style="margin-left: 1em;" type="button">Multiple</button>
        </v-col>
        <v-col cols="12" md="6"  v-if="entryType === 'única'">
          <h3 class="josefin-sans labels" style="color: #000;">Hora de entrada</h3>
          <v-text-field placeholder="hh:mm aa" variant="solo" hide-details="auto" bg-color=#F6F9FB v-model="entryTime" :active="menu_entry" readonly required class="form-label small-input">
            <img src="/src/assets/img/clock.svg" style="position: absolute; right: 15px;"/>
            <v-menu v-model="menu_entry" :close-on-content-click="false" activator="parent" transition="scale-transition">
              <v-time-picker v-if="menu_entry" v-model="entryTime" full-width format="24hr" ></v-time-picker>
            </v-menu>
          </v-text-field>
        </v-col>
        <v-col cols="12" md="6"  v-if="entryType === 'múltiple'" class="multi-hours">
          <h3 class="josefin-sans labels" style="color: #000;">Hora de inicio</h3>
          <v-text-field placeholder="hh:mm aa" variant="solo" hide-details="auto" bg-color=#F6F9FB v-model="startTime" :active="menu_start" readonly required class="hour-label small-input">
            <img src="/src/assets/img/clock.svg" style="position: absolute; right: 15px;"/>
            <v-menu v-model="menu_start" :close-on-content-click="false" activator="parent" transition="scale-transition">
              <v-time-picker v-if="menu_start" v-model="startTime" full-width format="24hr" ></v-time-picker>
            </v-menu>
          </v-text-field>
        </v-col>
          <v-col cols="12" md="6"  v-if="entryType === 'múltiple'"  class="multi-hours">
          <h3 class="josefin-sans labels" style="color: #000;">Hora de fin</h3>
          <v-text-field placeholder="hh:mm aa" variant="solo" hide-details="auto" bg-color=#F6F9FB v-model="endTime" :active="menu_end" readonly required class="hour-label small-input">
            <img src="/src/assets/img/clock.svg" style="position: absolute; right: 15px;"/>
            <v-menu v-model="menu_end" :close-on-content-click="false" activator="parent" transition="scale-transition">
              <v-time-picker v-if="menu_end" v-model="endTime" full-width format="24hr"></v-time-picker>
            </v-menu>
          </v-text-field>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="12" md="6" v-if="entryType === 'única'">
          <h3 class="josefin-sans labels" style="color: #000;">Fecha de entrada</h3>
          <v-text-field v-model="entryDateFormatted" placeholder="Entrada" readonly :active="menu" hide-details="auto" bg-color=#F6F9FB variant="solo" class="form-label small-input">
            <img src="/src/assets/img/calendar.svg" style="position: absolute; right: 15px;">
            <v-menu v-model="menu" :close-on-content-click="false" activator="parent" transition="scale-transition">
              <v-locale-provider locale="es">
                <v-date-picker full-width v-model="entryDate" v-if="menu" color=#12453B></v-date-picker>
              </v-locale-provider>
            </v-menu>
          </v-text-field>
        </v-col>

        <v-col cols="12" md="6" v-if="entryType === 'múltiple'">
          <h3 class="josefin-sans labels" style="color: #000;">Fechas de entrada</h3>
          <v-date-input
      v-model="dateRange"
      variant="outlined"
      bg-color=#F6F9FB
      color=#12453B
      prepend-icon=""
      append-inner-icon="$calendar"
      max-width="368"
      multiple="range"
    ></v-date-input>
        </v-col>


        <v-col cols="12" md="6" v-if="entryType === 'múltiple'">
          <h3 class="josefin-sans labels" style="color: #000;">Días de entrada:</h3>
          <v-date-input 
      v-model="selectedDays"
      :max="maxDate"
      :min="minDate"
      variant="outlined"
      bg-color=#F6F9FB
      color=#12453B
      prepend-icon=""
      append-inner-icon="$calendar"
      max-width="368"
      multiple
      :disabled="!dateRange || dateRange.length === 0"
    ></v-date-input>
        </v-col>
      </v-row>
      <div class="d-flex justify-center">
        <v-btn class="josefin-sans btn-generar" style="margin-top: 4em; margin-bottom: 5em;" @click="createSingleRequest()" v-if="entryType === 'única'">
          <span style="text-transform: none; font-size: 18px;" class="jostfin-sans">Solicitar</span>
        </v-btn>
        <v-btn class="josefin-sans btn-generar" style="margin-top: 4em; margin-bottom: 5em;" @click="createMultipleRequest()" v-if="entryType === 'múltiple'">
          <span style="text-transform: none; font-size: 18px;" class="jostfin-sans">Solicitar</span>
        </v-btn>
      </div>
    </v-form>
  </div>
  </template>
  
  <script>
  import Navbar from '../components/navbar.vue';
  import { ref } from 'vue';
  import { format } from 'date-fns';
  import { es } from 'date-fns/locale';
  import { jwtDecode } from 'jwt-decode';
  import services from '../services';
  import Swal from 'sweetalert2';
  
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
        startTime: null,
        endTime: null,
        entryDate: new Date(),
        formattedDate: '',
        menu: false,
        menu_entry: false,
        menu_start: false,
        menu_end: false,
        selectedDays: [],
        role: 'visitor',
        isAdmin: false,
        isResident: false,
        isVisitor: false,
        dateRange: null,
        selectedDays: null,
        minDate: null,
        maxDate: null,
      }
    },

    watch: {
      dateRange(newRange) {
        if (newRange && newRange.length > 0) {
          this.minDate = this.formatDate(newRange[0])
          this.maxDate = this.formatDate(newRange[newRange.length - 1])
          this.selectedDays = null 
        } else {
          this.minDate = null
          this.maxDate = null
        }
      },
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
        this.selectedDays = isSelected ? this.selectedDays.filter(d => d !== day) : [...this.selectedDays, day];
      },
      setRoleInLocalStorage(role) {
      localStorage.setItem('userRole', role);
      this.role = role;
    },
     setRole(){
      const token = localStorage.getItem('token');
      const decoded = jwtDecode(token);
      if (decoded.roles.includes('ROLE_main resident')){
        this.isAdmin = true;
      }
      if (decoded.roles.includes('ROLE_resident')){
        this.isResident = true;
      }
     },
     async createSingleRequest(){
      const dui = this.dui;
      const email = this.email;
      const entryDate = this.entryDate;
      const entryTime = this.entryTime;
      const singleRequest = await services.residentAdmin.requestSingle(dui, email, entryDate, entryTime);
      if(singleRequest.status === 201){
        Swal.fire({
          icon: 'success',
          title: 'Solicitud creada con éxito',
          showConfirmButton: false,
          timer: 2000
        });
        this.dui = '';
        this.email = '';
        this.entryDate = null;
        this.entryTime = null;
        setTimeout(() => {
          this.$router.push('/qr');
        }, 2000);

      }else{
        Swal.fire({
          icon: 'error',
          title: 'Error al crear la solicitud',
          showConfirmButton: true,
        })
      }
     },
     formatDate(date) {
        const d = new Date(date)
        const month = '' + (d.getMonth() + 1)
        const day = '' + d.getDate()
        const year = d.getFullYear()
        return [year, month.padStart(2, '0'), day.padStart(2, '0')].join('-')
      },
      async createMultipleRequest(){
        const dui = this.dui;
        const email = this.email;
        const startTime = this.startTime;
        const endTime = this.endTime;
        const selectedDays = this.selectedDays;
        const multipleRequest = await services.residentAdmin.requestMultiple(dui, email, selectedDays,startTime, endTime);
        console.log(multipleRequest);
        if(multipleRequest.status === 201){
          Swal.fire({
            icon: 'success',
            title: 'Solicitud creada con éxito',
            showConfirmButton: false,
            timer: 2000
          });
          setTimeout(() => {
            this.$router.push('/qr');
          }, 2000);
  
        }else{
          Swal.fire({
            icon: 'error',
            title: 'Error al crear la solicitud',
            showConfirmButton: true,
          })
        }
      }
    },
    setup() {
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
    }, mounted() {

    },
    created() {
      this.setRole();
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
  
  .dropdown-container {
    width: 100%;
  }
  
  .dropdown-days-wrapper {
    width: 100%;
    display: flex;
    flex-wrap: wrap;
  }
  
  .dropdown-day {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 41px;
    height: 41px;
    border-radius: 50%;
    margin: 3px;
    margin-top: 1rem;
    flex-shrink: 0;
    cursor: pointer;
    background-color: #F6F9FB;
    color: #838383;
    font-family: "Josefin Sans";
    font-size: 22px;
    font-style: normal;
    font-weight: 700;
    line-height: normal;
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
  
  .btn-generar {
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
  
  h4 {
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
  
  .google-image {
    width: 66px;
    height: 66px;
    border-radius: var(--Flat, 66px);
  }
  
  .qr-div {
    background-color: #F6F9FB;
    width: 426px;
    height: 425px;
    margin: auto;
    margin-top: 3em;
    border-radius: 30px;
  }
  
  .qr-content {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  }
  
  .qr-img {
    width: 125px;
    height: 125px;
  }
  
  .qr-text {
    width: 249px;
    margin-top: 2rem;
    line-height: normal;
  }
  
  .form-label {
    border-radius: 4px;
    border: 1px solid #12453B;
    color: #12453B;
    width: 100%;
  }

  .hour-label {
    border-radius: 4px;
    border: 1px solid #12453B;
    color: #12453B;
    width: 100%;
  }

  .multi-hours {
    max-width: 25%;
  }

  .form{
    margin-top: 3em; 
    width: 50%;
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
  
  .small-input {
    max-width: 300px;
  }
  
  #Form-title {
    width: 50%;
    color: #000;
    text-align: center;
    justify-self: center;
    font-feature-settings: 'clig' off, 'liga' off;
    font-family: "Josefin Sans";
    font-size: 32px;
    font-style: normal;
    font-weight: 600;
    line-height: normal;
  }
  
  .flex-column {
    align-items: center;
    margin-left: 10px;
  }

  @media (max-width: 1355px) {
    .multi-hours {
    max-width: 50%;
  }

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

    .form{
    width: 85%;
  }
  
    #Form-title {
      text-align: center;
    }

    .multi-hours {
    max-width: 100%;
  }
  
    .flex-column {
      align-items: start;
      margin-left: 50px;
    }
  }
  </style>
  