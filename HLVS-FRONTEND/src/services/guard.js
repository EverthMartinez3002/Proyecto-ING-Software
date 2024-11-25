import axiosInstance from "../plugins/axios";

const requestAnonymous = async (headline, comment) => {
    const response = await axiosInstance.post('/api/entries/anonymous', {
        headline: headline,
        comment: comment
    },{
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    });

    return response;
}

const scanQr = async (qrToken) => {
    const response = await axiosInstance.get('/api/qr/scan', {
        params: {
            token: qrToken
        }, 
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    })

    return response
}

export default {
    requestAnonymous,
    scanQr
}