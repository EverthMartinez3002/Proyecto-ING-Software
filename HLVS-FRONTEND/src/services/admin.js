import axiosInstance from "../plugins/axios";

const getHouses = async (filter,page,per_page) => {
    const response = await axiosInstance.get('/api/houses', {
        params: {
            filter: filter,
            page: page,
            per_page: per_page
        },
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
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
            'Authorization': `Bearer ${localStorage.getItem('token')}`
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
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    });

    return response;
}

const getHouseByNumberHouse = async (houseNumber) => {
    const response = await axiosInstance.get(`/api/houses/number/${houseNumber}`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    });

    return response
}

const getDevices = async () => {
    const response = await axiosInstance.get('/api/devices', {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
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
            'Authorization': `Bearer ${localStorage.getItem('token')}`
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
            'Authorization': `Bearer ${localStorage.getItem('token')}`
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
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    });

    return response
}

const updateLimitTime = async(newLimit) => {
    const response = await axiosInstance.put('/api/requests/updateLimitTime', {    
    }, {
        params: {
            newLimit: newLimit
        },
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    });
    return response
}

const updateQrDuration = async(duration) => {
    const response = await axiosInstance.put('/api/qr/expiration', {
    }, {
        params: {
            duration: duration
        },
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    });

    return response
}

const getHistoryEntriesGraphics = async() => {
    const response = await axiosInstance.get('/api/entries/counts/combined', {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    })

    return response
}

const getAllHistoryEntries = async(page,per_page) => {
    const response = await axiosInstance.get('/api/entries', {
        params: {
            page: page,
            per_page: per_page
        },
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    })

    return response
}

const exportExcel = async(startDate,endDate,entryTipe) => {
    const response = await axiosInstance.get('/api/entries/export', {
        params: {
            startDate: startDate,
            endDate: endDate,
            entryTipe: entryTipe
        },
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    })

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
 updateDevice,
 updateLimitTime,
 updateQrDuration,
 getHistoryEntriesGraphics,
 getAllHistoryEntries,
 exportExcel
}