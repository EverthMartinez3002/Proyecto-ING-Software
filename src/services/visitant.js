import axiosInstance from "../plugins/axios";
const token = localStorage.getItem('token');

const generateQr = async (uuid) => {
    const response = await axiosInstance.post('/api/qr/generate-by-user',{
        token: uuid
    },
    {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
    
    return response
}

export default {
    generateQr
}