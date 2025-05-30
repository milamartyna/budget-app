import axios, { AxiosInstance, AxiosRequestConfig } from "axios";

const BASE_URL = "http://localhost:8080";

/** Single Axios instance reused everywhere */
const client: AxiosInstance = axios.create({
    baseURL: BASE_URL,
    timeout: 10_000,
});

/* ---------- Optional interceptors ---------- */
client.interceptors.request.use((config) => {
    // e.g. attach JWT if present
    const token = localStorage.getItem("token");
    if (token) config.headers!.Authorization = `Bearer ${token}`;
    return config;
});

client.interceptors.response.use(
    (response) => response,
    (error) => {
        // global error handling, logging, toast, …
        return Promise.reject(error);
    }
);

function get<T>(url: string, cfg?: AxiosRequestConfig) {
    return client.get<T>(url, cfg).then((r) => r.data);
}

function post<T, B = unknown>(url: string, body: B, cfg?: AxiosRequestConfig) {
    return client.post<T>(url, body, cfg).then((r) => r.data);
}

function del<T>(url: string, cfg?: AxiosRequestConfig) {
    return client.delete<T>(url, cfg).then((r) => r.data);
}

export const http = { get, post, delete: del };
