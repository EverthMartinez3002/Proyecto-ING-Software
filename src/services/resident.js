import axiosInstance from "../plugins/axios";

const getAllHistoryEntriesbyUser = async (page,per_page) => {
    const response = await axiosInstance.get('/api/entries/by-user', {
        params: {
            page: page,
            per_page: per_page
        },
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    })

    return response;
}

const getAllRequestbyUser = async (page,per_page) => {
    const response = await axiosInstance.get('/api/requests/user-requests', {
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

export default {
    getAllHistoryEntriesbyUser,
    getAllRequestbyUser
}