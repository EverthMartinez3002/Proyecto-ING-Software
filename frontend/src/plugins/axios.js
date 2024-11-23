import axios from 'axios';

const axiosInstance = axios.create({
    baseURL: import.meta.env.VUE_APP_API_URL || 'http://3.140.250.202:8080',
 });

 export default axiosInstance;