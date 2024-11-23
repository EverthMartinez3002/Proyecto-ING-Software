import axiosInstance from "../plugins/axios";

const generateQr = async (uuid) => {
    const response = await axiosInstance.post('/api/qr/generate-by-user',{
        token: uuid
    },
    {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    });
    
    return response
}

export default {
    generateQr
}