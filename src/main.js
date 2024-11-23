import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import router from '../src/routes/router';
import { VTimePicker } from 'vuetify/labs/components'
import { VNumberInput } from 'vuetify/labs/components'
import { VDateInput } from 'vuetify/labs/components'
import * as VueQrcodeReader from "vue-qrcode-reader";
import services from '../src/services/index.js';
import { createPinia } from 'pinia'
import vue3GoogleLogin from 'vue3-google-login';

const mycomponents = {
    ...components,
    VTimePicker,
    VNumberInput,
    VDateInput
}

const vuetify = createVuetify({
    components: mycomponents,
    directives,
    icons: {
        defaultSet: 'mdi',
    },
})

const pinia = createPinia();

const gAuthOptions = {
    clientId: '226707301038-jespe8au3vt9j90siqtu8u9pacap30bu.apps.googleusercontent.com',
    scope: 'email profile',
    prompt: 'consent',
    fetch_basic_profile: true
}
  
createApp(App)
.use(router)
.use(vuetify)
.use(VueQrcodeReader)
.provide('services', services)
.use(pinia)
.use(vue3GoogleLogin, {
    clientId: '226707301038-jespe8au3vt9j90siqtu8u9pacap30bu.apps.googleusercontent.com'
})
.mount('#app')