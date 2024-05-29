import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import router from '../src/routes/router';
import { VTimePicker } from 'vuetify/labs/components'

const mycomponents = {
    ...components,
    VTimePicker
}

const vuetify = createVuetify({
    components: mycomponents,
    directives,
    icons: {
        defaultSet: 'mdi',
    },
})

createApp(App)
.use(router)
.use(vuetify)
.mount('#app')