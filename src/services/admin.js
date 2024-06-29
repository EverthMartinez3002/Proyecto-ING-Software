import axiosInstance from "../plugins/axios";
const token = localStorage.getItem('token');

const getHouses = async () => {
    const response = await axiosInstance.get('/api/houses', {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });

    return response;
}

const newHouse = async (houseNumber,address,residentNumber,leaderEmail) => {
    const response = await axiosInstance.post('/api/houses', {
        houseNumber: houseNumber,
        address: address,
        residentNumber: residentNumber,
        leaderEmail: leaderEmail
    }, {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });

    return response;
}

const updateHouse = async (uuId,residents,address,residentNumber,houseNumber) => {
    const response = await axiosInstance.put(`/api/houses/${uuId}`, {
        houseNumber: houseNumber,
        address: address,
        residentNumber: residentNumber,
        residents: residents
    }, {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });

    return response;
}

const getDevices = async () => {
    const response = await axiosInstance.get('/api/devices', {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });

    return response
}

const newDevice = async (serialNumber,location,securityGuardEmail) => {
    const response = await axiosInstance.post('/api/devices', {
        serialNumber: serialNumber,
        location: location,
        securityGuardEmail: securityGuardEmail
    }, {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });

    return response;
}

export default {
 getHouses,
 newHouse,
 getDevices,
 newDevice,
 updateHouse,
}