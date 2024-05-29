import { createWebHistory, createRouter } from "vue-router";

import Home from "../views/Home.vue";
import QrGenerator from "../views/QrGenerator.vue";
import History from "../views/History.vue";

const routes = [
    {
        path: '/', component: Home,
    },
    {
        path: '/qr', component: QrGenerator
    },
    {
        path: '/history', component: History
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router;