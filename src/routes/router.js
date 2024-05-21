import { createWebHistory, createRouter } from "vue-router";

import Home from "../components/Home.vue";
import QrGenerator from "../components/QrGenerator.vue";

const routes = [
    {
        path: '/', component: Home,
    },
    {
        path: '/qr', component: QrGenerator
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router;