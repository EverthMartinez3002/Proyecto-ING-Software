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

const updateHouse = async (uuId,residents,address,residentNumber,leaderEmail,houseNumber) => {
    const response = await axiosInstance.put(`/api/houses/${uuId}`, {
        houseNumber: houseNumber,
        address: address,
        residentNumber: residentNumber,
        leaderEmail: leaderEmail,
        residents: residents
    }, {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });

    return response;
}

const getHouseByNumberHouse = async (houseNumber) => {
    const response = await axiosInstance.get(`/api/houses/number/${houseNumber}`, {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });

    return response
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

const getDevicebyId = async (uuId) => {
    const response = await axiosInstance.get('/api/devices/detail', {
        params: {
            id: uuId
        },
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
    
    return response;
}

const updateDevice = async (serialNumber,location,securityGuardEmail) => {
    const response = await axiosInstance.patch(`/api/devices/update/${serialNumber}`, {
        location: location,
        securityGuardEmail: securityGuardEmail
    }, 
    {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });

    return response
}

export default {
 getHouses,
 newHouse,
 updateHouse,
 getHouseByNumberHouse,
 getDevices,
 newDevice,
 getDevicebyId,
 updateDevice
}