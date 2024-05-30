import { createWebHistory, createRouter } from "vue-router";

import Home from "../views/Home.vue";
import QrGenerator from "../views/QrGenerator.vue";
import History from "../views/History.vue";
import RequestFamily from "../views/requestFamily.vue";

const routes = [
    {
        path: '/', component: Home,
    },
    {
        path: '/qr', component: QrGenerator
    },
    {
        path: '/history', component: History
    },
    {
        path: '/family-request', component: RequestFamily
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router;