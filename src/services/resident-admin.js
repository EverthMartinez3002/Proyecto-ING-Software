import axiosInstance from "../plugins/axios";

const login = async (email, password, name) => {

    const response = await axiosInstance.post('/api/auth/register-or-login', {
        email: email,
        name: name,
        password: password
    });

    return response;
}

export default {
    login
}