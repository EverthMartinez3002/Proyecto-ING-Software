import { createWebHistory, createRouter } from "vue-router";

import Home from "../views/Home.vue";
import QrGenerator from "../views/QrGenerator.vue";
import History from "../views/History.vue";
import RequestFamily from "../views/requestFamily.vue";
import FamilyManagement from "../views/family-management.vue";
import ConfirmRequest from "../views/confirm-request.vue";
import ApprovedRequest from "../views/approved-request.vue";
import HouseManagement from "../views/house-management.vue";
import NewHouse from "../views/new-house.vue";
import HistoryEntries from "../views/history-entries.vue";
import Devices from "../views/devices.vue";
import NewDevice from "../views/new-device.vue";
import EditHouse from "../views/edit-house.vue";
import EditDevice from "../views/edit-device.vue";
import GuardManagement from "../views/guard-management.vue";
import StateRequest from "../views/state-request.vue";
import QrError from "../views/qr-error.vue";
import AddResidents from "../views/add-residents.vue";
import { jwtDecode } from "jwt-decode";

const routes = [
    {
        path: '/', component: Home, meta: { roles: [] }
    },
    {
        path: '/qr', component: QrGenerator, meta: { roles: ['ROLE_main resident', 'ROLE_resident', 'ROLE_visitant'] }
    },
    {
        path: '/history', component: History, meta: { roles: ['ROLE_main resident', 'ROLE_resident', 'ROLE_visitant'] }
    },
    {
        path: '/family-request', component: RequestFamily, meta: { roles: ['ROLE_main resident'] }
    },
    {
        path: '/family-management', component: FamilyManagement, meta: { roles: ['ROLE_main resident'] }
    },
    {
        path: '/confirm-request/:id/:resident/:visitor', component: ConfirmRequest, meta: { roles: ['ROLE_main resident'] }
    },
    {
        path: '/approved-request', component: ApprovedRequest, meta: { roles: ['ROLE_main resident'] }
    },
    {
        path: '/house-management', component: HouseManagement, meta: { roles: ['ROLE_admin'] }
    },
    {
        path: '/new-house', component: NewHouse, meta: { roles: ['ROLE_admin'] }
    },
    {
        path: '/edit-house/:id', component: EditHouse, props: true, meta: { roles: ['ROLE_admin'] }
    },
    {
        path: '/history-entries', component: HistoryEntries, meta: { roles: ['ROLE_admin'] }
    },
    {
        path: '/devices', component: Devices, meta: { roles: ['ROLE_admin'] }
    },
    {
        path: '/new-device', component: NewDevice, meta: { roles: ['ROLE_admin'] }
    },
    {
        path: '/edit-device/:id', component: EditDevice, meta: { roles: ['ROLE_admin'] }
    },
    {
        path: '/guard-management', component: GuardManagement, meta: { roles: ['ROLE_security guard'] }
    },
    {
        path: '/state-request', component: StateRequest, meta: { roles: ['ROLE_resident'] }
    },
    {
        path: '/qr-error', component: QrError, meta: { roles: ['ROLE_visitant'] }
    },
    {
        path: '/add-residents/:id', component: AddResidents, meta: { roles: ['ROLE_admin'] }
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
})

router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('token');

    if(!token){
        if(to.path !== '/'){
            next('/')
        } else {
            next()
        }
    }else{
        const decoded = jwtDecode(token);
        const userRoles = decoded.roles;
        const routeRoles = to.meta.roles;

        if (routeRoles.length === 0 || routeRoles.some(role => userRoles.includes(role))) {
            next();
        } else {
            next('/');
        }
    }
})

export default router;