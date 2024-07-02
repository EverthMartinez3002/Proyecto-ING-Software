import axiosInstance from "../plugins/axios";
const token = localStorage.getItem('token');

const getAllHistoryEntriesbyUser = async (page,per_page) => {
    const response = await axiosInstance.get('/api/entries/by-user', {
        params: {
            page: page,
            per_page: per_page
        },
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })

    return response;
}

const getAllRequestbyUser = async () => {
    const response = await axiosInstance.get('/api/requests/user-requests', {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })

    return response
}

export default {
    getAllHistoryEntriesbyUser,
    getAllRequestbyUser
}