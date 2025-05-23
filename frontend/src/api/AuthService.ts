import axios from "axios";

const unauthenticated = axios.create({
    baseURL: "http://localhost:8080",
    timeout: 10_000,
});

export async function login(username: string, password: string): Promise<string> {
    const response = await unauthenticated.post("/auth/login", { name: username, password });
    return response.data;
}
