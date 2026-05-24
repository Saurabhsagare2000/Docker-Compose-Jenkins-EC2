import axios from "axios";
import { getToken } from "../auth/index";

// Base URL through ingress
export const BASE_URL = "/api/v1";

// Public Axios
export const myAxios = axios.create({
  baseURL: BASE_URL,
});

// Private Axios (for secured APIs)
export const privateAxios = axios.create({
  baseURL: BASE_URL,
});

// Interceptor for adding JWT token
privateAxios.interceptors.request.use(
  (config) => {
    const token = getToken();

    console.log("token is", token);

    if (token) {
      config.headers["Authorization"] = `Bearer ${token}`;
    }

    return config;
  },
  (error) => Promise.reject(error)
);