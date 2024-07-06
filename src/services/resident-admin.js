import axiosInstance from "../plugins/axios";
const login = async (email, password, name) => {

    const response = await axiosInstance.post('/api/auth/register-or-login', {
        email: email,
        name: name,
        password: password
    });

    return response;
}

const requestSingle = async (dui,email,entryDate,entryTime) => {
    const response = await axiosInstance.post('/api/requests/create/single', {
        dui: dui,
        email: email,
        entryDate: entryDate,
        entryTime: entryTime
    }, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    });

    return response;
}

const requestMultiple = async (dui,email,entryDates,hour1,hour2) => {
    const response = await axiosInstance.post('api/requests/create/multiple', {
        dui: dui,
        email: email,
        entryDates: entryDates,
        hour1: hour1,
        hour2: hour2
    }, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    });

    return response;
}

const getAllPending = async () => {
    const response = await axiosInstance.get('/api/requests/AllPending', {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    });

    return response
}

const getRequestById = async (requestId,residentName,visitorName,page,per_page) => {
    const response = await axiosInstance.get(`/api/requests/request/${requestId}`, {
        params: {
            residentName: residentName,
            visitorName: visitorName,
            page: page,
            per_page: per_page
        },
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    });

    return response
}

const approveRequest = async (requestId,residentName,visitorName,status) => {
    const response = await axiosInstance.patch(`/api/requests/request/${requestId}`, {
    },{
        params: {
            residentName: residentName,
            visitorName: visitorName,
            status: status
        },
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }    
    });

    return response
}

const getHistoryByHouse = async (page, per_page) => {
    const response = await axiosInstance.get('/api/entries/by-house', {
        params: {
            page: page,
            per_page: per_page
        },
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    });

    return response
}

const getAllAproved = async () => {
    const response = await axiosInstance.get('/api/requests/AllApproved', {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    });

    return response
}

const getFamilyMembers = async () => {
    const response = await axiosInstance.get('/api/family', {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    })

    return response
}

const updateFamilyMembers = async (residents) => {
    const response = await axiosInstance.put('/api/family', 
        residents,
    {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    });

    return response
}

const generateQrAdmin = async (uuid) => {
    const response = await axiosInstance.post('/api/qr/generate-for-role', {
        token: uuid
    },{
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    });

    return response
}

export default {
    login,
    requestSingle,
    requestMultiple,
    getAllPending,
    getRequestById,
    approveRequest,
    getHistoryByHouse,
    getAllAproved,
    getFamilyMembers,
    updateFamilyMembers,
    generateQrAdmin
}